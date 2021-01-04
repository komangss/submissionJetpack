package com.komangss.submissionjetpack.business.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.network.mappers.NetworkMapper
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse

class FakeCatalogRepository(
    private val catalogRemoteDataSource: CatalogRemoteDataSource,
    private val networkMapper: NetworkMapper
) : CatalogDataSource {

    override fun getAllMovies(): LiveData<List<Movie>> {
        val movieResults = MutableLiveData<List<Movie>>()
        catalogRemoteDataSource.getAllMovies(object : CatalogRemoteDataSource.LoadMoviesCallback {
            override fun onMoviesReceived(movieResponses: List<MovieResponse>) {
                movieResults.postValue(
                    networkMapper.movieResponseListToMovieList(movieResponses)
                )
            }

        })
        return movieResults
    }

    override fun getAllTvShows(): LiveData<List<TvShow>> {
        val tvShowResults = MutableLiveData<List<TvShow>>()
        catalogRemoteDataSource.getAllTvShows(object : CatalogRemoteDataSource.LoadTvShowsCallback {
            override fun onTvShowsReceived(tvShowsResponse: List<TvShowResponse>) {
                tvShowResults.postValue(
                    networkMapper.tvShowResponseListToDomain(tvShowsResponse)
                )
            }

        })
        return tvShowResults
    }

    override fun getMovieById(id : Int) : MutableLiveData<Movie> {
        val movieResult = MutableLiveData<Movie>()
        catalogRemoteDataSource.getMovieById(id, object : CatalogRemoteDataSource.LoadMovieByIdCallback {
            override fun onMovieReceived(movieResponse: MovieResponse) {
                movieResult.postValue(networkMapper.movieResponseToDomain(movieResponse))
            }

        })
        return movieResult
    }

    override fun getTvShowById(id: Int): LiveData<TvShow> {
        val tvShowResult = MutableLiveData<TvShow>()
        catalogRemoteDataSource.getTvShowById(id) {
            tvShowResult.postValue(networkMapper.tvShowResponseToDomain(it))
        }
        return tvShowResult
    }
}