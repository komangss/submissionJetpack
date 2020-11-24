package com.komangss.submissionjetpack.framework.network.services

import com.komangss.submissionjetpack.BuildConfig
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.MovieResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatalogServices {
    @GET("discover/movie?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getMovies() : MovieResultResponse
}