package com.komangss.submissionjetpack.framework.network.services

import com.komangss.submissionjetpack.BuildConfig
import com.komangss.submissionjetpack.framework.network.model.MovieResultResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResultResponse
import retrofit2.http.GET

interface CatalogServices {
    @GET("discover/movie?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getMovies() : MovieResultResponse

    @GET("tv/popular?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getTvShows() : TvShowResultResponse
}