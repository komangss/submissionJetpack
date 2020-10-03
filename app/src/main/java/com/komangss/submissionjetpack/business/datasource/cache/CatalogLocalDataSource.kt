package com.komangss.submissionjetpack.business.datasource.cache

import androidx.lifecycle.LiveData
import com.komangss.submissionjetpack.framework.cache.dao.CatalogDao
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity


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

    fun getAllMovies(): LiveData<List<MovieEntity>> = catalogDao.getMovies()

    fun insertMovies(movies: List<MovieEntity>) = catalogDao.insertMovies(movies)
}