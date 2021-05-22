package com.komangss.submissionjetpack.di

import android.content.Context
import androidx.room.Room
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.framework.cache.CatalogDatabase
import com.komangss.submissionjetpack.framework.cache.dao.CatalogDao
import com.komangss.submissionjetpack.framework.mapper.CatalogMovieMapper
import com.komangss.submissionjetpack.framework.mapper.CatalogTvShowMapper
import com.komangss.submissionjetpack.framework.network.services.CatalogServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideCatalogRemoteDataSource(catalogServices: CatalogServices): CatalogRemoteDataSource {
        return CatalogRemoteDataSource(catalogServices)
    }

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
    fun provideCatalogLocalDataSource(catalogDao: CatalogDao): CatalogLocalDataSource {
        return CatalogLocalDataSource(catalogDao)
    }

    @Singleton
    @Provides
    fun provideCatalogDatabase(@ApplicationContext context: Context): CatalogDatabase =
        Room.databaseBuilder(
            context,
            CatalogDatabase::class.java,
            "catalog_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideCatalogDao(catalogDatabase: CatalogDatabase): CatalogDao = catalogDatabase.catalogDao

    @Singleton
    @Provides
    fun provideMovieMapper(): CatalogMovieMapper = CatalogMovieMapper()

    @Singleton
    @Provides
    fun provideTvShowMapper(): CatalogTvShowMapper = CatalogTvShowMapper()


}