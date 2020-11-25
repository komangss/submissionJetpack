package com.komangss.submissionjetpack.business.repository

import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.cache.mappers.MovieCacheMapper
import com.komangss.submissionjetpack.framework.network.mappers.MovieNetworkMapper
import com.komangss.submissionjetpack.framework.network.utils.ApiResponse
import com.komangss.submissionjetpack.framework.network.utils.ErrorResponse
import com.komangss.submissionjetpack.utils.AppExecutors
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatalogRepository
private constructor(
    private val catalogRemoteDataSource : CatalogRemoteDataSource,
    private val catalogLocalDataSource: CatalogLocalDataSource,
    private val networkMapper: MovieNetworkMapper,
    private val cacheMapper: MovieCacheMapper,
    private val appExecutors: AppExecutors
) : CatalogDataSource {
    companion object {
        @Volatile
        private var instance: CatalogRepository? = null

        fun getInstance(
            catalogRemoteDataSource: CatalogRemoteDataSource,
            catalogLocalDataSource: CatalogLocalDataSource,
            networkMapper: MovieNetworkMapper,
            cacheMapper: MovieCacheMapper,
            appExecutors: AppExecutors
        ): CatalogRepository =
            instance ?: synchronized(this) {
                instance ?: CatalogRepository(
                    catalogRemoteDataSource, catalogLocalDataSource, networkMapper, cacheMapper, appExecutors
                )
            }
    }

//    TODO : cache the result from remote
    @ExperimentalCoroutinesApi
    override suspend fun getAllMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.InProgress)
            when (val movieApiResponse = catalogRemoteDataSource.getAllMovies()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(networkMapper.responseListToMovieList(movieApiResponse.value.results)))
                }
                is ApiResponse.GenericError -> {
                    val code = movieApiResponse.code
                    val error = movieApiResponse.error
                    emit(Resource.Error(code, error))
                }
                ApiResponse.NetworkError -> emit(Resource.Error(0, ErrorResponse("Unknown Error")))
            }
        }
    }

//    override fun getAllTvShows(): LiveData<List<TvShowEntity>> {
//    }
//
//    override fun getMovieById(id: Int): LiveData<Movie> {
//    }

//    override fun getAllTvShows(): LiveData<List<TvShowEntity>> {
//        val tvShowResults = MutableLiveData<List<TvShowEntity>>()
//        catalogRemoteDataSource.getAllTvShows(object : CatalogRemoteDataSource.LoadTvShowsCallback {
//            override fun onTvShowsReceived(tvShowsResponse: List<TvShowResponse>) {
//                val tvShowResponseList = ArrayList<TvShowEntity>()
//                for (response in tvShowsResponse) {
//                    val tvShow = TvShowEntity(
//                        response.id,
//                        response.title,
//                        response.description,
//                        response.image,
//                        response.releaseDate,
//                        response.rating
//                    )
//                    tvShowResponseList.add(tvShow)
//                }
//                tvShowResults.postValue(tvShowResponseList)
//            }
//
//        })
//        return tvShowResults
//    }

//    override fun getMovieById(id: Int): MutableLiveData<Movie> {
//        val movieResult = MutableLiveData<Movie>()
//        catalogRemoteDataSource.getMovieById(
//            id,
//            object : CatalogRemoteDataSource.LoadMovieByIdCallback {
//                override fun onMovieReceived(movieResponse: MovieResponse) {
//                    movieResult.postValue(networkMapper.mapFromResponse(movieResponse))
//                }
//
//            })
//        return movieResult
//    }
}