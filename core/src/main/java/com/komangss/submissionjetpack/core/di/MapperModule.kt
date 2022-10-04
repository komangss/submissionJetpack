package com.komangss.submissionjetpack.core.di

import com.komangss.submissionjetpack.core.mapper.CatalogMovieMapper
import com.komangss.submissionjetpack.core.mapper.CatalogTvShowMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Singleton
    @Provides
    fun provideMovieMapper(): CatalogMovieMapper = CatalogMovieMapper()

    @Singleton
    @Provides
    fun provideTvShowMapper(): CatalogTvShowMapper = CatalogTvShowMapper()

}