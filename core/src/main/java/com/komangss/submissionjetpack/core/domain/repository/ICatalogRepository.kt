package com.komangss.submissionjetpack.core.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.core.domain.model.Movie
import com.komangss.submissionjetpack.core.domain.model.TvShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface ICatalogRepository {
    suspend fun getAllMovies(): Flow<Resource<List<Movie>>>

    suspend fun getAllTvShows(): Flow<Resource<List<TvShow>>>

    suspend fun updateTvShow(tvShow: TvShow)

    suspend fun updateMovie(movie: Movie)

    @ExperimentalCoroutinesApi
    suspend fun getMovieById(id: Int): Flow<Resource<Movie>>

    @ExperimentalCoroutinesApi
    suspend fun getTvShowById(id: Int): Flow<Resource<TvShow>>

    fun getFavoriteMovies(): LiveData<PagedList<Movie>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShow>>
}