package com.komangss.submissionjetpack.business.datasource.network

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
import com.komangss.submissionjetpack.framework.network.utils.ApiResponse
import com.komangss.submissionjetpack.utils.EspressoIdlingResources
import com.komangss.submissionjetpack.utils.JsonHelper

class CatalogRemoteDataSource private constructor(private val jsonHelper: JsonHelper){
    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: CatalogRemoteDataSource? = null

        fun getInstance(helper: JsonHelper): CatalogRemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: CatalogRemoteDataSource(helper)
            }
    }

    private val handler = Handler()

    fun getAllMovies() : LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResources.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        handler.postDelayed({
            resultMovie.value = ApiResponse.success(jsonHelper.loadMovies())
            EspressoIdlingResources.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultMovie
    }

    interface LoadMoviesCallback {
        fun onMoviesReceived(movieResponses: List<MovieResponse>)
    }

    fun getAllTvShows(callback : LoadTvShowsCallback) {
        handler.postDelayed({callback.onTvShowsReceived(jsonHelper.loadTvShow())}, SERVICE_LATENCY_IN_MILLIS)
    }
    interface LoadTvShowsCallback {
        fun onTvShowsReceived(tvShowsResponse : List<TvShowResponse>)
    }

    fun getMovieById(id : Int, callback : LoadMovieByIdCallback) {
        EspressoIdlingResources.increment()
        handler.postDelayed({
            callback.onMovieReceived(jsonHelper.loadMovieById(id))
            EspressoIdlingResources.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    interface LoadMovieByIdCallback {
        fun onMovieReceived(movieResponse: MovieResponse)
    }
}