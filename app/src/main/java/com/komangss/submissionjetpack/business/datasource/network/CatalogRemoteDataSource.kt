package com.komangss.submissionjetpack.business.datasource.network

import com.komangss.submissionjetpack.framework.network.model.MovieDetailResponse
import com.komangss.submissionjetpack.framework.network.model.MovieResultResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowDetailResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResultResponse
import com.komangss.submissionjetpack.framework.network.services.CatalogServices
import com.komangss.submissionjetpack.framework.network.utils.ApiResponse
import com.komangss.submissionjetpack.framework.network.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class CatalogRemoteDataSource private constructor(private val catalogServices: CatalogServices){
    companion object {
        @Volatile
        private var instance: CatalogRemoteDataSource? = null

        fun getInstance(catalogServices: CatalogServices): CatalogRemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: CatalogRemoteDataSource(catalogServices)
            }
    }

    @ExperimentalCoroutinesApi
    suspend fun getAllMovies() : Flow<ApiResponse<MovieResultResponse>> {
        return safeApiCall(Dispatchers.IO) {catalogServices.getMovies()}
    }

    @ExperimentalCoroutinesApi
    suspend fun getAllTvShows() : Flow<ApiResponse<TvShowResultResponse>> {
        return safeApiCall(Dispatchers.IO) {catalogServices.getTvShows()}
    }

    @ExperimentalCoroutinesApi
    suspend fun getMovieById(id: Int) : Flow<ApiResponse<MovieDetailResponse>> {
        return safeApiCall(Dispatchers.IO) {catalogServices.getMovieById(id)}
    }

    @ExperimentalCoroutinesApi
    suspend fun getTvShowById(id: Int) : Flow<ApiResponse<TvShowDetailResponse>> {
        return safeApiCall(Dispatchers.IO) {catalogServices.getTvShowById(id)}
    }

//    fun getMovieById(id: Int, onMovieReceived: (movieResponse: MovieResponse) -> Unit) {
//        EspressoIdlingResources.increment()
//        handler.postDelayed({
//            jsonHelper.loadMovieById(id)?.let { onMovieReceived(it) }
//            EspressoIdlingResources.decrement()
//        }, SERVICE_LATENCY_IN_MILLIS)
//    }
//
//    fun getTvShowById(id: Int, onTvShowReceived: (tvShowResponse: TvShowResponse) -> Unit) {
//        EspressoIdlingResources.increment()
//        handler.postDelayed({
//            jsonHelper.loadTvShowById(id)?.let { onTvShowReceived(it) }
//            EspressoIdlingResources.decrement()
//        }, SERVICE_LATENCY_IN_MILLIS)
//    }
}