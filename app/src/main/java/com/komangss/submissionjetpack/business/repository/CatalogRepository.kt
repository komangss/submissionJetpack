package com.komangss.submissionjetpack.business.repository

import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.mapper.MapperInterface
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.utils.networkBoundResource
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class CatalogRepository
private constructor(
    private val catalogRemoteDataSource : CatalogRemoteDataSource,
    private val catalogLocalDataSource: CatalogLocalDataSource,
    private val catalogMovieMapper: MapperInterface<Movie, MovieEntity, MovieResponse>
) : CatalogDataSource {
    companion object {
        @Volatile
        private var instance: CatalogRepository? = null

        fun getInstance(
            catalogRemoteDataSource: CatalogRemoteDataSource,
            catalogLocalDataSource: CatalogLocalDataSource,
            mapperInterface: MapperInterface<Movie, MovieEntity, MovieResponse>
        ): CatalogRepository =
            instance ?: synchronized(this) {
                instance ?: CatalogRepository(
                    catalogRemoteDataSource, catalogLocalDataSource, mapperInterface
                )
            }
    }

//    TODO : cache the result from remote
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getAllMovies(): Flow<Resource<List<Movie>>> {
        return networkBoundResource(
            fetchFromLocal = { catalogLocalDataSource.getAllMovies() },
            shouldFetchFromRemote = { it == null},
            fetchFromRemote = { catalogRemoteDataSource.getAllMovies()},
            processRemoteResponse = { },
            saveRemoteData = {
                catalogLocalDataSource.insertMovies(catalogMovieMapper.responsesToEntities(it.results))
            },
            mapFromCache = {catalogMovieMapper.entitiesToDomains(it)}
        )
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