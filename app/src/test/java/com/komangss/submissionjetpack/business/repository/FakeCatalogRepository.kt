package com.komangss.submissionjetpack.business.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.network.mappers.NetworkMapper

class FakeCatalogRepository(
    private val catalogRemoteDataSource: CatalogRemoteDataSource,
    private val networkMapper: NetworkMapper
) : CatalogDataSource {

    override fun getAllMovies(): LiveData<List<Movie>> {
        val movieResults = MutableLiveData<List<Movie>>()
        catalogRemoteDataSource.getAllMovies {
            movieResults.postValue(
                networkMapper.movieResponseListToMovieList(it)
            )
        }
        return movieResults
    }

    override fun getAllTvShows(): LiveData<List<TvShow>> {
        val tvShowResults = MutableLiveData<List<TvShow>>()
        catalogRemoteDataSource.getAllTvShows {
            tvShowResults.postValue(
                networkMapper.tvShowResponseListToDomain(it)
            )
        }
        return tvShowResults
    }

    override fun getMovieById(id : Int) : MutableLiveData<Movie> {
        val movieResult = MutableLiveData<Movie>()
        catalogRemoteDataSource.getMovieById(id) {
            movieResult.postValue(networkMapper.movieResponseToDomain(it))
        }
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