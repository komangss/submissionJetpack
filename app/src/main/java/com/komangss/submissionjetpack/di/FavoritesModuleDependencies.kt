package com.komangss.submissionjetpack.di

import com.komangss.submissionjetpack.core.domain.usecase.CatalogUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoritesModuleDependencies {
    fun catalogUseCase(): CatalogUseCase
}