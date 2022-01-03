package com.komangss.submissionjetpack.core.di

import com.komangss.submissionjetpack.core.data.source.remote.CatalogRemoteDataSource
import com.komangss.submissionjetpack.core.data.source.remote.network.CatalogServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCatalogService(retrofit: Retrofit): CatalogServices {
        return retrofit.create(CatalogServices::class.java)
    }

    @Singleton
    @Provides
    fun provideCatalogRemoteDataSource(catalogServices: CatalogServices): CatalogRemoteDataSource {
        return CatalogRemoteDataSource(catalogServices)
    }
}