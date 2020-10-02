package com.komangss.submissionjetpack.business.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.business.datasource.cache.LocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.cache.mappers.MovieCacheMapper
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.framework.network.mappers.MovieNetworkMapper
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
import com.komangss.submissionjetpack.framework.network.utils.ApiResponse
import com.komangss.submissionjetpack.framework.network.utils.NetworkBoundResource
import com.komangss.submissionjetpack.utils.AppExecutors
import com.komangss.submissionjetpack.vo.Resource

class CatalogRepository
private constructor(
    private val catalogRemoteDataSource: CatalogRemoteDataSource,
    private val catalogLocalDataSource: LocalDataSource,
    private val networkMapper: MovieNetworkMapper,
    private val cacheMapper: MovieCacheMapper,
    private val appExecutors: AppExecutors
) : CatalogDataSource {
    companion object {
        @Volatile
        private var instance: CatalogRepository? = null

        fun getInstance(
            catalogRemoteDataSource: CatalogRemoteDataSource,
            catalogLocalDataSource: LocalDataSource,
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

    override fun getAllMovies(): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieResponse>, List<MovieEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> =
                catalogLocalDataSource.getAllMovies()

            override fun shouldFetch(data: List<MovieEntity>?): Boolean =
                (data == null) || (data.isEmpty())

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                catalogRemoteDataSource.getAllMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieEntities: MutableList<MovieEntity> = ArrayList()
                for (movieResponse in data) {
                    movieEntities.add(
                        MovieEntity(
                            movieResponse.id,
                            movieResponse.title,
                            movieResponse.director,
                            movieResponse.description,
                            movieResponse.image,
                            movieResponse.releaseDate,
                            movieResponse.rating
                        )
                    )
                    catalogLocalDataSource.insertMovies(movieEntities)
                }
            }

            override fun mapFromLocalTypeToResult(data: List<MovieEntity>): List<Movie> =
                cacheMapper.cacheListToMovieList(data)

        }.asLiveData()
    }

    override fun getAllTvShows(): LiveData<List<TvShowEntity>> {
        val tvShowResults = MutableLiveData<List<TvShowEntity>>()
        catalogRemoteDataSource.getAllTvShows(object : CatalogRemoteDataSource.LoadTvShowsCallback {
            override fun onTvShowsReceived(tvShowsResponse: List<TvShowResponse>) {
                val tvShowResponseList = ArrayList<TvShowEntity>()
                for (response in tvShowsResponse) {
                    val tvShow = TvShowEntity(
                        response.id,
                        response.title,
                        response.description,
                        response.image,
                        response.releaseDate,
                        response.rating
                    )
                    tvShowResponseList.add(tvShow)
                }
                tvShowResults.postValue(tvShowResponseList)
            }

        })
        return tvShowResults
    }

    override fun getMovieById(id: Int): MutableLiveData<Movie> {
        val movieResult = MutableLiveData<Movie>()
        catalogRemoteDataSource.getMovieById(
            id,
            object : CatalogRemoteDataSource.LoadMovieByIdCallback {
                override fun onMovieReceived(movieResponse: MovieResponse) {
                    movieResult.postValue(networkMapper.mapFromResponse(movieResponse))
                }

            })
        return movieResult
    }
}