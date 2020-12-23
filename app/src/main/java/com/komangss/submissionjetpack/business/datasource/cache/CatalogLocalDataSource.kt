package com.komangss.submissionjetpack.business.datasource.cache

import com.komangss.submissionjetpack.framework.cache.dao.CatalogDao
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
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
}