package com.komangss.submissionjetpack.di

import android.content.Context
import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.framework.cache.CatalogDatabase
import com.komangss.submissionjetpack.framework.mapper.CatalogMovieMapper
import com.komangss.submissionjetpack.framework.mapper.CatalogTvShowMapper
import com.komangss.submissionjetpack.framework.network.instance.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideCatalogDataSource(@ApplicationContext context: Context) : CatalogDataSource {
        return CatalogRepository.getInstance(
            CatalogRemoteDataSource.getInstance(RetrofitBuilder.catalogServices),
            CatalogLocalDataSource.getInstance(CatalogDatabase.getInstance(context).catalogDao),
            CatalogMovieMapper(),
            CatalogTvShowMapper()
        )
    }
}