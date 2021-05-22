package com.komangss.submissionjetpack.business.datasource.cache

import com.komangss.submissionjetpack.framework.cache.dao.CatalogDao
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatalogLocalDataSource @Inject constructor(private val catalogDao: CatalogDao) {

    fun getAllMovies(): Flow<List<MovieEntity>> = catalogDao.getMovies()

    suspend fun insertMovies(movies: List<MovieEntity>) = catalogDao.insertMovies(movies)

    fun getAllTvShows(): Flow<List<TvShowEntity>> = catalogDao.getTvShows()

    suspend fun insertTvShows(tvShows: List<TvShowEntity>) = catalogDao.insertTvShows(tvShows)

    fun getMovieById(id: Int) = catalogDao.getMovieById(id)

    fun getTvShowById(id: Int) = catalogDao.getTvShowById(id)

    suspend fun updateMovie(movie: MovieEntity) {
        catalogDao.updateMovie(movie)
    }

    suspend fun updateTvShow(tvShow: TvShowEntity) {
        catalogDao.updateTvShow(tvShow)
    }

    fun getFavoriteMovies() = catalogDao.getFavoriteMovies()

    fun getFavoriteTvShows() = catalogDao.getFavoriteTvShows()

    suspend fun insertMovie(movie: MovieEntity) {
        catalogDao.insertMovie(movie)
    }

    suspend fun insertTvShow(tvShowEntity: TvShowEntity) {
        catalogDao.insertTvShow(tvShowEntity)
    }
}