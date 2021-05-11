package com.komangss.submissionjetpack.business.repository

import androidx.paging.DataSource
import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCatalogRepositoryAndroidTest : CatalogDataSource {

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getAllMovies(): Flow<Resource<List<Movie>>> {
        return throw NullPointerException()
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getAllTvShows(): Flow<Resource<List<TvShow>>> {
        return throw NullPointerException()
    }

    @ExperimentalCoroutinesApi
    override suspend fun getMovieById(id: Int): Flow<Resource<Movie>> = flow {
    }

    @ExperimentalCoroutinesApi
    override suspend fun getTvShowById(id: Int): Flow<Resource<TvShow>> = flow {
    }

    override fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> {
        return throw NullPointerException()
    }

    override fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity> {
        return throw NullPointerException()
    }

    override suspend fun updateTvShow(tvShow: TvShow) {
        return throw NullPointerException()
     }

    override suspend fun updateMovie(movie: Movie) {
        return throw NullPointerException()
    }
}