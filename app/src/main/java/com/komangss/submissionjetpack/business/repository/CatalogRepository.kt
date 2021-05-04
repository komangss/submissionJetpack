package com.komangss.submissionjetpack.business.repository

import androidx.paging.DataSource
import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.framework.mapper.MapperInterface
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
import com.komangss.submissionjetpack.framework.network.utils.ErrorResponse
import com.komangss.submissionjetpack.framework.network.utils.networkBoundResource
import com.komangss.submissionjetpack.utils.EspressoIdlingResources
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatalogRepository
@Inject constructor(
    val catalogRemoteDataSource: CatalogRemoteDataSource,
    val catalogLocalDataSource: CatalogLocalDataSource,
    val catalogMovieMapper: MapperInterface<Movie, MovieEntity, MovieResponse>,
    val catalogTvShowMapper: MapperInterface<TvShow, TvShowEntity, TvShowResponse>
) : CatalogDataSource {
    companion object {
        @Volatile
        private var instance: CatalogRepository? = null

        fun getInstance(
            catalogRemoteDataSource: CatalogRemoteDataSource,
            catalogLocalDataSource: CatalogLocalDataSource,
            catalogMovieMapper: MapperInterface<Movie, MovieEntity, MovieResponse>,
            catalogTvShowMapper: MapperInterface<TvShow, TvShowEntity, TvShowResponse>
        ): CatalogRepository =
            instance ?: synchronized(this) {
                instance ?: CatalogRepository(
                    catalogRemoteDataSource, catalogLocalDataSource, catalogMovieMapper,
                    catalogTvShowMapper
                )
            }
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getAllMovies(): Flow<Resource<List<Movie>>> {
        return networkBoundResource(
            fetchFromLocal = { catalogLocalDataSource.getAllMovies() },
            shouldFetchFromRemote = { it?.isEmpty() == true },
            fetchFromRemote = { catalogRemoteDataSource.getAllMovies() },
            processRemoteResponse = { },
            saveRemoteData = { movieResultResponse ->
                movieResultResponse.results?.let { movieResponse ->
                    catalogMovieMapper.responsesToEntities(
                        movieResponse
                    )
                }?.let { movieEntities -> catalogLocalDataSource.insertMovies(movieEntities) }
            },
            mapFromCache = { catalogMovieMapper.entitiesToDomains(it) },
            mapFromRemote = { catalogMovieMapper.responsesToDomains(it.results ?: listOf()) }
        )
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getAllTvShows(): Flow<Resource<List<TvShow>>> {
        return networkBoundResource(
            fetchFromRemote = { catalogRemoteDataSource.getAllTvShows() },
            shouldFetchFromRemote = { it?.isEmpty() == true },
            fetchFromLocal = { catalogLocalDataSource.getAllTvShows() },
            processRemoteResponse = {},
            saveRemoteData = { tvShowResultResponse ->
                tvShowResultResponse.results?.let { tvShowResponse ->
                    catalogTvShowMapper.responsesToEntities(
                        tvShowResponse
                    )
                }?.let { tvShowEntities -> catalogLocalDataSource.insertTvShows(tvShowEntities) }
            },
            mapFromCache = { catalogTvShowMapper.entitiesToDomains(it) },
            mapFromRemote = { catalogTvShowMapper.responsesToDomains(it.results ?: listOf()) }
        )
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getMovieById(id: Int): Flow<Resource<Movie>> {
        return networkBoundResource(
            fetchFromRemote = { catalogRemoteDataSource.getMovieById(id) },
            shouldFetchFromRemote = { it == null },
            fetchFromLocal = { catalogLocalDataSource.getMovieById(id) },
            processRemoteResponse = {},
            saveRemoteData = {},
            mapFromCache = { catalogMovieMapper.entityToDomain(it) },
            mapFromRemote = { catalogMovieMapper.responseToDomain(it) },
            shouldCache = { false }
        )
    }

//    = flow {
//        emit(Resource.InProgress)
//        EspressoIdlingResources.increment()
//
//        val result = catalogLocalDataSource.getMovieById(id)
//        if (result == null) {
//            val entity = catalogMovieMapper.responseToEntity(catalogRemoteDataSource.getMovieById(id))
//            val response = catalogMovieMapper.entityToDomain(entity)
//            emit(Resource.Success(response))
//        } else {
//            emit(Resource.Success(catalogMovieMapper.entityToDomain(result)))
//        }
//        EspressoIdlingResources.decrement()
//    }

    @ExperimentalCoroutinesApi
    override suspend fun getTvShowById(id: Int): Flow<Resource<TvShow>> = flow {
        emit(Resource.InProgress)
        EspressoIdlingResources.increment()
        val result = catalogLocalDataSource.getTvShowById(id)
        if (result == null) {
            emit(Resource.Error(null, ErrorResponse("Data Not Found")))
        } else {
            emit(Resource.Success(catalogTvShowMapper.entityToDomain(result)))
        }
        EspressoIdlingResources.decrement()
    }

    override fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> {
        return catalogLocalDataSource.getFavoriteMovies()
    }

    override fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity> {
        return catalogLocalDataSource.getFavoriteTvShows()
    }

    override suspend fun setTvShowFavorite(tvShow: TvShow) {
        tvShow.isFavorite = !tvShow.isFavorite
        catalogLocalDataSource.updateTvShowFavorite(catalogTvShowMapper.domainToEntity(tvShow))
    }

    override suspend fun setMovieFavorite(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        catalogLocalDataSource.updateMovieFavorite(catalogMovieMapper.domainToEntity(movie))
    }
}