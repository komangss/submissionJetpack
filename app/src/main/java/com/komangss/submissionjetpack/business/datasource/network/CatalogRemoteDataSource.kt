package com.komangss.submissionjetpack.business.datasource.network

import android.os.Handler
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
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

    fun getAllMovies(callback: LoadMoviesCallback) {
//        EspressoIdlingResource.increment()
//        val resultCourse = MutableLiveData<ApiResponse<List<CourseResponse>>>()
        handler.postDelayed({

        }, SERVICE_LATENCY_IN_MILLIS)
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