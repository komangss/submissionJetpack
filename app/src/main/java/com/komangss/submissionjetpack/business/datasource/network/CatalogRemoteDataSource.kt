package com.komangss.submissionjetpack.business.datasource.network

import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.MovieResultResponse
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
    suspend fun getMovieById(movieId : Int) : Flow<ApiResponse<MovieResponse>> {
        return safeApiCall(Dispatchers.IO) {catalogServices.getMovieById(movieId)}
    }
}