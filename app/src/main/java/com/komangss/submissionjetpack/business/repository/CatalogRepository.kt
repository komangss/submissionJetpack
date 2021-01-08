package com.komangss.submissionjetpack.business.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse

class CatalogRepository
private constructor(
    private val catalogRemoteDataSource : CatalogRemoteDataSource,
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
            mapperInterface: MapperInterface<Movie, MovieEntity, MovieResponse>,
             catalogTvShowMapper: MapperInterface<TvShow, TvShowEntity, TvShowResponse>
        ): CatalogRepository =
            instance ?: synchronized(this) {
                instance ?: CatalogRepository(
                    catalogRemoteDataSource, catalogLocalDataSource, mapperInterface,
                    catalogTvShowMapper
                )
            }
    }

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

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun getAllTvShows(): Flow<Resource<List<TvShow>>> {
        return networkBoundResource(
            fetchFromRemote = {catalogRemoteDataSource.getAllTvShows()},
            shouldFetchFromRemote = {it == null},
            fetchFromLocal = {catalogLocalDataSource.getAllTvShows() },
            processRemoteResponse = {},
            saveRemoteData = {
                catalogLocalDataSource.insertTvShows(catalogTvShowMapper.responsesToEntities(it.results))
            },
            mapFromCache = {catalogTvShowMapper.entitiesToDomains(it)}
        )
    }

//    override fun getMovieById(id : Int) : MutableLiveData<Movie> {
//        val movieResult = MutableLiveData<Movie>()
//        catalogRemoteDataSource.getMovieById(id, object : CatalogRemoteDataSource.LoadMovieByIdCallback {
//            override fun onMovieReceived(movieResponse: MovieResponse) {
//                movieResult.postValue(networkMapper.mapFromResponse(movieResponse))
//            }
//
//        })
//        return movieResult
//    }

//    override fun getTvShowById(id: Int): LiveData<TvShow> {
//        val tvShowResult = MutableLiveData<TvShow>()
//        catalogRemoteDataSource.getTvShowById(id) {
//            tvShowResult.postValue(networkMapper.tvShowResponseToDomain(it))
//        }
//        return tvShowResult
//    }
}