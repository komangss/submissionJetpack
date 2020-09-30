package com.komangss.submissionjetpack.business.datasource.cache

import androidx.lifecycle.LiveData
import com.komangss.submissionjetpack.framework.cache.dao.CatalogDao
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity


class LocalDataSource private constructor(
    private val catalogDao: CatalogDao
) {
    companion object {
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(catalogDao: CatalogDao): LocalDataSource {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = LocalDataSource(catalogDao)
                }
                return instance
            }
        }
    }

    fun getAllMovies(): LiveData<List<MovieEntity>> = catalogDao.getMovies()

}