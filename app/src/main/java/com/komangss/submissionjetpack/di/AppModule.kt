package com.komangss.submissionjetpack.di

import com.komangss.submissionjetpack.core.domain.usecase.CatalogInteractor
import com.komangss.submissionjetpack.core.domain.usecase.CatalogUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideCatalogUseCase(catalogInteractor: CatalogInteractor): CatalogUseCase

}