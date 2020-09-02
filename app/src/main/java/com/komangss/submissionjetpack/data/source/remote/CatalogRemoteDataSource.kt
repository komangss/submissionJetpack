package com.komangss.submissionjetpack.data.source.remote

import android.os.Handler
import com.komangss.submissionjetpack.data.source.local.entity.MovieEntity
import com.komangss.submissionjetpack.data.source.remote.response.MovieResponse
import com.komangss.submissionjetpack.data.source.remote.response.TvShowResponse
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
        handler.postDelayed({callback.onMoviesReceived(jsonHelper.loadMovies())}, SERVICE_LATENCY_IN_MILLIS)
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
}