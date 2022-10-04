package com.komangss.submissionjetpack.core.di

import com.komangss.submissionjetpack.core.data.CatalogRepository
import com.komangss.submissionjetpack.core.domain.repository.ICatalogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class, MapperModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(catalogRepository: CatalogRepository): ICatalogRepository
}