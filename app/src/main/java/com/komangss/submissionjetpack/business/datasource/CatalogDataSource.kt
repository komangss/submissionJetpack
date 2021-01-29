package com.komangss.submissionjetpack.business.datasource

import androidx.paging.DataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface CatalogDataSource {
    suspend fun getAllMovies() : Flow<Resource<List<Movie>>>

    suspend fun getAllTvShows() : Flow<Resource<List<TvShow>>>

    suspend fun setTvShowFavorite(tvShow: TvShow)

    suspend fun setMovieFavorite(movie: Movie)

    @ExperimentalCoroutinesApi
    suspend fun getMovieById(id: Int): Flow<Resource<Movie>>

    @ExperimentalCoroutinesApi
    suspend fun getTvShowById(id: Int): Flow<Resource<TvShow>>

    fun getFavoriteMovies() : DataSource.Factory<Int, MovieEntity>

    fun getFavoriteTvShows() : DataSource.Factory<Int, TvShowEntity>
}