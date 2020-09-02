package com.komangss.submissionjetpack.di

import android.content.Context
import com.komangss.submissionjetpack.data.CatalogRepository
import com.komangss.submissionjetpack.data.source.remote.CatalogRemoteDataSource
import com.komangss.submissionjetpack.utils.JsonHelper

object Injection {
    fun provideCatalogRepository(context: Context) : CatalogRepository =
        CatalogRepository.getInstance(
            CatalogRemoteDataSource.getInstance(JsonHelper(context))
        )
}