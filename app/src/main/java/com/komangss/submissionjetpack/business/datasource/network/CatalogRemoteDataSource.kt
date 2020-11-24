package com.komangss.submissionjetpack.business.datasource.network

import com.komangss.submissionjetpack.framework.network.model.MovieResultResponse
import com.komangss.submissionjetpack.framework.network.services.CatalogServices
import com.komangss.submissionjetpack.framework.network.utils.ApiResponse
import com.komangss.submissionjetpack.framework.network.utils.safeApiCall
//import com.komangss.submissionjetpack.utils.JsonHelper
import kotlinx.coroutines.Dispatchers

class CatalogRemoteDataSource private constructor(private val catalogServices: CatalogServices){
    companion object {
//        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: CatalogRemoteDataSource? = null

        fun getInstance(catalogServices: CatalogServices): CatalogRemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: CatalogRemoteDataSource(catalogServices)
            }
    }

//    private val handler = Handler()

//    TODO : Add Idling Resource
    suspend fun getAllMovies() : ApiResponse<MovieResultResponse> {
//        EspressoIdlingResources.increment()
//        val movieListResult = catalogServices.getMovies(BuildConfig.TMDB_API_KEY)
//        return try {
//            if (movieListResult.isSuccessful || movieListResult.code() == 200) {
//                EspressoIdlingResources.decrement()
//                return movieListResult.body() // TODO : Remove non null asserted call
//            } else {
//                EspressoIdlingResources.decrement()
//                return null
//            }
//        } catch (exception : Exception) {
//
//        }
        return safeApiCall(Dispatchers.IO) {catalogServices.getMovies()}
    }

//    fun getAllTvShows(callback : LoadTvShowsCallback) {
//        handler.postDelayed({callback.onTvShowsReceived(jsonHelper.loadTvShow())}, SERVICE_LATENCY_IN_MILLIS)
//    }
//    interface LoadTvShowsCallback {
//        fun onTvShowsReceived(tvShowsResponse : List<TvShowResponse>)
//    }
//
//    fun getMovieById(id : Int, callback : LoadMovieByIdCallback) {
//        EspressoIdlingResources.increment()
//        handler.postDelayed({
//            callback.onMovieReceived(jsonHelper.loadMovieById(id))
//            EspressoIdlingResources.decrement()
//        }, SERVICE_LATENCY_IN_MILLIS)
//    }
//
//    interface LoadMovieByIdCallback {
//        fun onMovieReceived(movieResponse: MovieResponse)
//    }
}