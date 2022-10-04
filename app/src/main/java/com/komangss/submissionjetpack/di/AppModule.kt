package com.komangss.submissionjetpack.di

import com.komangss.submissionjetpack.core.domain.usecase.CatalogInteractor
import com.komangss.submissionjetpack.core.domain.usecase.CatalogUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideCatalogUseCase(catalogInteractor: CatalogInteractor): CatalogUseCase

}