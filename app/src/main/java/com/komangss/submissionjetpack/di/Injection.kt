package com.komangss.submissionjetpack.di

import android.content.Context
import com.komangss.submissionjetpack.business.datasource.cache.LocalDataSource
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.framework.cache.CatalogDatabase
import com.komangss.submissionjetpack.framework.cache.mappers.MovieCacheMapper
import com.komangss.submissionjetpack.framework.network.mappers.MovieNetworkMapper
import com.komangss.submissionjetpack.utils.AppExecutors
import com.komangss.submissionjetpack.utils.JsonHelper

object Injection {
    fun provideCatalogRepository(context: Context) : CatalogRepository =
        CatalogRepository.getInstance(
            CatalogRemoteDataSource.getInstance(JsonHelper(context)),
            LocalDataSource.getInstance(CatalogDatabase.getInstance(context).catalogDao),
            MovieNetworkMapper(),
            MovieCacheMapper(),
            AppExecutors()
        )
}