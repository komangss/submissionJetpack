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
import com.komangss.submissionjetpack.framework.network.utils.networkBoundResource
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class CatalogRepository
private constructor(
    private val catalogRemoteDataSource: CatalogRemoteDataSource,
    private val catalogLocalDataSource: CatalogLocalDataSource,
    private val catalogMovieMapper: MapperInterface<Movie, MovieEntity, MovieResponse>,
    private val catalogTvShowMapper: MapperInterface<TvShow, TvShowEntity, TvShowResponse>
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
            saveRemoteData = {catalogLocalDataSource.insertMovie(catalogMovieMapper.responseToEntity(it))},
            mapFromCache = { catalogMovieMapper.entityToDomain(it) },
            mapFromRemote = { catalogMovieMapper.responseToDomain(it) },
            shouldCache = { true }
        )
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getTvShowById(id: Int): Flow<Resource<TvShow>> {
        return networkBoundResource(
            fetchFromRemote = { catalogRemoteDataSource.getTvShowById(id) },
            shouldFetchFromRemote = { it == null },
            fetchFromLocal = { catalogLocalDataSource.getTvShowById(id) },
            processRemoteResponse = {},
            saveRemoteData = {catalogLocalDataSource.insertTvShow(catalogTvShowMapper.responseToEntity(it))},
            mapFromCache = { catalogTvShowMapper.entityToDomain(it) },
            mapFromRemote = { catalogTvShowMapper.responseToDomain(it) },
            shouldCache = { true }
        )
    }

    override fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> {
        return catalogLocalDataSource.getFavoriteMovies()
    }

    override fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity> {
        return catalogLocalDataSource.getFavoriteTvShows()
    }

    override suspend fun setTvShowFavorite(tvShow: TvShow) {
        catalogLocalDataSource.updateTvShowFavorite(catalogTvShowMapper.domainToEntity(tvShow))
    }

    override suspend fun setMovieFavorite(movie: Movie) {
        catalogLocalDataSource.updateMovieFavorite(catalogMovieMapper.domainToEntity(movie))
    }
}