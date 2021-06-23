package com.komangss.submissionjetpack.framework.network.services

import com.komangss.submissionjetpack.BuildConfig
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.MovieResultResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResultResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogServices {
    @GET("discover/movie?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getMovies(): MovieResultResponse

    @GET("tv/popular?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getTvShows(): TvShowResultResponse

    @GET("movie/{movieId}?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getMovieById(@Path("movieId") movieId: Int) : MovieResponse

    @GET("/tv/{tvShowId}?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getTvShowById(@Path("tvShowId") tvShowId: Int) : TvShowResponse
}