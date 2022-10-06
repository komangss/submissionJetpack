package com.komangss.submissionjetpack.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.core.domain.model.Movie
import com.komangss.submissionjetpack.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface CatalogUseCase {
    suspend fun getAllMovies(): Flow<Resource<List<Movie>>>

    suspend fun getAllTvShows(): Flow<Resource<List<TvShow>>>

    suspend fun updateTvShow(tvShow: TvShow)

    suspend fun updateMovie(movie: Movie)

    suspend fun getMovieById(id: Int): Flow<Resource<Movie>>

    suspend fun getTvShowById(id: Int): Flow<Resource<TvShow>>

    fun getFavoriteMovies(): LiveData<PagedList<Movie>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShow>>
}