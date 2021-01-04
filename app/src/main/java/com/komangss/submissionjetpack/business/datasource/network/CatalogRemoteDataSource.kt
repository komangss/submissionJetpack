package com.komangss.submissionjetpack.business.datasource.network

import android.os.Handler
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
import com.komangss.submissionjetpack.utils.EspressoIdlingResources
import com.komangss.submissionjetpack.utils.JsonHelper

class CatalogRemoteDataSource private constructor(private val jsonHelper: JsonHelper) {
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

    fun getAllMovies(onMoviesReceived: (movieResponses: List<MovieResponse>) -> Unit) {
        handler.postDelayed(
            { onMoviesReceived(jsonHelper.loadMovies()) },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    fun getAllTvShows(onTvShowsReceived : (tvShowsResponse: List<TvShowResponse>) -> Unit) {
        handler.postDelayed(
            { onTvShowsReceived(jsonHelper.loadTvShow()) },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    fun getMovieById(id: Int, onMovieReceived: (movieResponse: MovieResponse) -> Unit) {
        EspressoIdlingResources.increment()
        handler.postDelayed({
            jsonHelper.loadMovieById(id)?.let { onMovieReceived(it) }
            EspressoIdlingResources.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getTvShowById(id: Int, onTvShowReceived: (tvShowResponse: TvShowResponse) -> Unit) {
        EspressoIdlingResources.increment()
        handler.postDelayed({
            jsonHelper.loadTvShowById(id)?.let { onTvShowReceived(it) }
            EspressoIdlingResources.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
    }
}