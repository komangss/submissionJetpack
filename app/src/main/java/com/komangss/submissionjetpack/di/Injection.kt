package com.komangss.submissionjetpack.di

import android.content.Context
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.framework.network.mappers.NetworkMapper
import com.komangss.submissionjetpack.utils.JsonHelper

object Injection {
    fun provideCatalogRepository(context: Context) : CatalogRepository =
        CatalogRepository.getInstance(
            CatalogRemoteDataSource.getInstance(JsonHelper(context)),
            NetworkMapper()
        )
}