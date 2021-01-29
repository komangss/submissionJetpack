package com.komangss.submissionjetpack.business.datasource

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.flow.Flow

interface CatalogDataSource {
    suspend fun getAllMovies() : Flow<Resource<List<Movie>>>

    suspend fun getAllTvShows() : Flow<Resource<List<TvShow>>>

    suspend fun setTvShowFavorite(tvShow: TvShow)

    suspend fun setMovieFavorite(movie: Movie)
}