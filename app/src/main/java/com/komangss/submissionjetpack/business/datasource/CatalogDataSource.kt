package com.komangss.submissionjetpack.business.datasource

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface CatalogDataSource {
    suspend fun getAllMovies() : Flow<Resource<List<Movie>>>

    suspend fun getAllTvShows() : Flow<Resource<List<TvShow>>>

    suspend fun updateTvShow(tvShow: TvShow)

    suspend fun updateMovie(movie: Movie)

    @ExperimentalCoroutinesApi
    suspend fun getMovieById(id: Int): Flow<Resource<Movie>>

    @ExperimentalCoroutinesApi
    suspend fun getTvShowById(id: Int): Flow<Resource<TvShow>>

    fun getFavoriteMovies(): LiveData<PagedList<Movie>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShow>>
}