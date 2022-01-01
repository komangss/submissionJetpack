package com.komangss.submissionjetpack.core.data.source.remote

import com.komangss.submissionjetpack.core.data.source.remote.network.ApiResponse
import com.komangss.submissionjetpack.core.data.source.remote.network.CatalogServices
import com.komangss.submissionjetpack.core.data.source.remote.network.safeApiCall
import com.komangss.submissionjetpack.core.data.source.remote.response.MovieResponse
import com.komangss.submissionjetpack.core.data.source.remote.response.MovieResultResponse
import com.komangss.submissionjetpack.core.data.source.remote.response.TvShowResponse
import com.komangss.submissionjetpack.core.data.source.remote.response.TvShowResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatalogRemoteDataSource @Inject constructor(private val catalogServices: CatalogServices){

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

    @ExperimentalCoroutinesApi
    suspend fun getTvShowById(tvShowId : Int) : Flow<ApiResponse<TvShowResponse>> {
        return safeApiCall(Dispatchers.IO) {catalogServices.getTvShowById(tvShowId)}
    }
}