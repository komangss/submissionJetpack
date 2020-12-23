package com.komangss.submissionjetpack.di

import android.content.Context
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.framework.cache.CatalogDatabase
import com.komangss.submissionjetpack.framework.mapper.CatalogMovieMapper
import com.komangss.submissionjetpack.framework.network.instance.RetrofitBuilder

object Injection {
    fun provideCatalogRepository(context: Context) : CatalogRepository =
        CatalogRepository.getInstance(
            CatalogRemoteDataSource.getInstance(RetrofitBuilder.catalogServices),
            CatalogLocalDataSource.getInstance(CatalogDatabase.getInstance(context).catalogDao),
            CatalogMovieMapper()
        )
}