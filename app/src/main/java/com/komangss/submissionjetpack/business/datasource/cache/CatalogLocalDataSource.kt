package com.komangss.submissionjetpack.business.datasource.cache

import com.komangss.submissionjetpack.framework.cache.dao.CatalogDao
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import kotlinx.coroutines.flow.Flow

class CatalogLocalDataSource private constructor(
    private val catalogDao: CatalogDao
) {
    companion object {
        private var INSTANCE: CatalogLocalDataSource? = null
        fun getInstance(catalogDao: CatalogDao): CatalogLocalDataSource {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = CatalogLocalDataSource(catalogDao)
                }
                return instance
            }
        }
    }

    fun getAllMovies(): Flow<List<MovieEntity>> = catalogDao.getMovies()

    suspend fun insertMovies(movies: List<MovieEntity>) = catalogDao.insertMovies(movies)

    fun getAllTvShows(): Flow<List<TvShowEntity>> = catalogDao.getTvShows()

    suspend fun insertTvShows(tvShows: List<TvShowEntity>) = catalogDao.insertTvShows(tvShows)

    fun getMovieById(id : Int) = catalogDao.getMovieById(id)

    fun getTvShowById(id : Int) = catalogDao.getTvShowById(id)

    suspend fun updateMovieFavorite(movie : MovieEntity) {
        catalogDao.updateMovie(movie)
    }

    suspend fun updateTvShowFavorite(tvShow : TvShowEntity) {
        catalogDao.updateTvShow(tvShow)
    }

    fun getFavoriteMovies() = catalogDao.getFavoriteMovies()

    fun getFavoriteTvShows() = catalogDao.getFavoriteTvShows()

    suspend fun insertMovie(movie: MovieEntity) {
        catalogDao.updateMovie(movie)
    }

    suspend fun insertTvShow(tvShowEntity: TvShowEntity) {
        catalogDao.updateTvShow(tvShowEntity)
    }
}