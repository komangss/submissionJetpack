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
import com.komangss.submissionjetpack.business.domain.model.TvShowDetail
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.framework.network.model.MovieDetailResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowDetailResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
import com.komangss.submissionjetpack.framework.network.utils.ApiResponse
import com.komangss.submissionjetpack.utils.EspressoIdlingResources
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
            mapFromCache = { catalogMovieMapper.entitiesToDomains(it) }
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
            mapFromCache = { catalogTvShowMapper.entitiesToDomains(it) }
        )
    }

    @ExperimentalCoroutinesApi
    override suspend fun getMovieById(id: Int): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.InProgress)
        EspressoIdlingResources.increment()
        catalogRemoteDataSource.getMovieById(id).collect {
            when (it) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(movieDetailResponseToDomain(it.value)))
                    EspressoIdlingResources.decrement()
                }
                is ApiResponse.GenericError -> {
                    emit(Resource.Error(it.code, it.error))
                    EspressoIdlingResources.decrement()
                }
                ApiResponse.NetworkError -> {
                    emit(Resource.Error())
                    EspressoIdlingResources.decrement()
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getTvShowById(id: Int): Flow<Resource<TvShowDetail>> = flow {
        emit(Resource.InProgress)
        EspressoIdlingResources.increment()
        catalogRemoteDataSource.getTvShowById(id).collect {
            when (it) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(tvShowDetailResponseToDomain(it.value)))
                    EspressoIdlingResources.decrement()
                }
                is ApiResponse.GenericError -> {
                    emit(Resource.Error(it.code, it.error))
                    EspressoIdlingResources.decrement()
                }
                ApiResponse.NetworkError -> {
                    emit(Resource.Error())
                    EspressoIdlingResources.decrement()
                }
            }
        }
    }

    private fun tvShowDetailResponseToDomain(response: TvShowDetailResponse): TvShowDetail {
        return TvShowDetail(
            response.backdropPath,
            response.firstAirDate,
            response.genres,
            response.homepage,
            response.id,
            response.name,
            response.networks,
            response.description,
            response.popularity,
            response.posterPath,
            response.spokenLanguages,
            response.status,
            response.voteAverage,
            response.voteCount
        )
    }

    private fun movieDetailResponseToDomain(response: MovieDetailResponse): MovieDetail {
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
}