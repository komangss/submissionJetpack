package com.komangss.submissionjetpack.business.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.network.mappers.MovieNetworkMapper
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse

class CatalogRepository
private constructor(
    private val catalogRemoteDataSource: CatalogRemoteDataSource,
    private val networkMapper: MovieNetworkMapper
) : CatalogDataSource {
    companion object {
        @Volatile
        private var instance: CatalogRepository? = null

        fun getInstance(
            catalogRemoteDataSource: CatalogRemoteDataSource,
            networkMapper: MovieNetworkMapper
            ): CatalogRepository =
            instance ?: synchronized(this) {
                instance ?: CatalogRepository(catalogRemoteDataSource, networkMapper)
            }
    }

    override fun getAllMovies(): LiveData<List<Movie>> {
        val movieResults = MutableLiveData<List<Movie>>()
        catalogRemoteDataSource.getAllMovies(object : CatalogRemoteDataSource.LoadMoviesCallback {
            override fun onMoviesReceived(movieResponses: List<MovieResponse>) {
                movieResults.postValue(
                    networkMapper.responseListToMovieList(movieResponses)
                )
            }

        })
        return movieResults
    }

    override fun getAllTvShows(): LiveData<List<TvShowEntity>> {
        val tvShowResults = MutableLiveData<List<TvShowEntity>>()
        catalogRemoteDataSource.getAllTvShows(object : CatalogRemoteDataSource.LoadTvShowsCallback {
            override fun onTvShowsReceived(tvShowsResponse: List<TvShowResponse>) {
                val tvShowResponseList = ArrayList<TvShowEntity>()
                for (response in tvShowsResponse) {
//                    val tvShow = TvShowEntity (
//                        response.id,
//                        response.title,
//                        response.description,
//                        response.image,
//                        response.releaseDate,
//                        response.rating
//                    )
//                    tvShowResponseList.add(tvShow)
                }
                tvShowResults.postValue(tvShowResponseList)
            }

        })
        return tvShowResults
    }

    override fun getMovieById(id : Int) : MutableLiveData<Movie> {
        val movieResult = MutableLiveData<Movie>()
        catalogRemoteDataSource.getMovieById(id, object : CatalogRemoteDataSource.LoadMovieByIdCallback {
            override fun onMovieReceived(movieResponse: MovieResponse) {
                movieResult.postValue(networkMapper.mapFromResponse(movieResponse))
            }

        })
        return movieResult
    }
}