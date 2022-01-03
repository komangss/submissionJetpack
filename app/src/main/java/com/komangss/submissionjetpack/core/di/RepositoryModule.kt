package com.komangss.submissionjetpack.core.di

import com.komangss.submissionjetpack.core.data.CatalogRepository
import com.komangss.submissionjetpack.core.domain.repository.ICatalogRepository
import com.komangss.submissionjetpack.core.utils.mapper.CatalogMovieMapper
import com.komangss.submissionjetpack.core.utils.mapper.CatalogTvShowMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(catalogRepository: CatalogRepository): ICatalogRepository

    @Singleton
    @Provides
    fun provideMovieMapper(): CatalogMovieMapper = CatalogMovieMapper()

    @Singleton
    @Provides
    fun provideTvShowMapper(): CatalogTvShowMapper = CatalogTvShowMapper()

}