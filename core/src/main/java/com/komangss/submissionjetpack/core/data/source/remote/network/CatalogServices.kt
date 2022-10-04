package com.komangss.submissionjetpack.core.data.source.remote.network

import com.komangss.submissionjetpack.core.BuildConfig
import com.komangss.submissionjetpack.core.data.source.remote.response.MovieResponse
import com.komangss.submissionjetpack.core.data.source.remote.response.MovieResultResponse
import com.komangss.submissionjetpack.core.data.source.remote.response.TvShowResponse
import com.komangss.submissionjetpack.core.data.source.remote.response.TvShowResultResponse
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