package com.komangss.submissionjetpack.business.repository

import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.MovieDetail
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.mapper.MapperInterface
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.utils.networkBoundResource
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.framework.network.model.MovieDetailResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
import com.komangss.submissionjetpack.framework.network.utils.ApiResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

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
            shouldFetchFromRemote = { it === null },
            fetchFromRemote = { catalogRemoteDataSource.getAllMovies() },
            processRemoteResponse = { },
            saveRemoteData = {
                catalogLocalDataSource.insertMovies(catalogMovieMapper.responsesToEntities(it.results))
            },
            mapFromCache = { catalogMovieMapper.entitiesToDomains(it) }
        )
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getAllTvShows(): Flow<Resource<List<TvShow>>> {
        return networkBoundResource(
            fetchFromRemote = { catalogRemoteDataSource.getAllTvShows() },
            shouldFetchFromRemote = { it === null },
            fetchFromLocal = { catalogLocalDataSource.getAllTvShows() },
            processRemoteResponse = {},
            saveRemoteData = {
                catalogLocalDataSource.insertTvShows(catalogTvShowMapper.responsesToEntities(it.results))
            },
            mapFromCache = { catalogTvShowMapper.entitiesToDomains(it) }
        )
    }

    @ExperimentalCoroutinesApi
    override suspend fun getMovieById(id: Int): Flow<Resource<MovieDetail>> = flow {
        catalogRemoteDataSource.getMovieById(id).collect {
            when (it) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(movieDetailResponseToDomain(it.value)))
                }
                is ApiResponse.GenericError -> emit(Resource.Error(it.code, it.error))
                ApiResponse.NetworkError -> emit(Resource.Error())
            }
        }
    }

    private fun movieDetailResponseToDomain(response : MovieDetailResponse) : MovieDetail {
        return MovieDetail(
            adult = response.adult,
            posterPath = response.posterPath,
            backdropPath = response.backdropPath,
            genres = response.genres,
            id = response.id,
            originalLanguage = response.originalLanguage,
            description = response.description,
            popularity = response.popularity,
            releaseDate = response.releaseDate,
            status = response.status,
            tagLine = response.tagLine,
            title = response.title,
            video = response.video,
            voteAverage = response.voteAverage,
            voteCount = response.voteCount
        )
    }


//    override fun getTvShowById(id: Int): LiveData<TvShow> {
//        val tvShowResult = MutableLiveData<TvShow>()
//        catalogRemoteDataSource.getTvShowById(id) {
//            tvShowResult.postValue(networkMapper.tvShowResponseToDomain(it))
//        }
//        return tvShowResult
//    }
}