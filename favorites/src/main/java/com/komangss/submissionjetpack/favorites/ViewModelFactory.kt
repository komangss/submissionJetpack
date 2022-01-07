package com.komangss.submissionjetpack.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.komangss.submissionjetpack.core.domain.usecase.CatalogUseCase
import com.komangss.submissionjetpack.favorites.movie.MovieFavoriteViewModel
import com.komangss.submissionjetpack.favorites.tvshow.TvShowFavoriteViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val catalogUseCase: CatalogUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MovieFavoriteViewModel::class.java) -> {
                MovieFavoriteViewModel(catalogUseCase) as T
            }
            modelClass.isAssignableFrom(TvShowFavoriteViewModel::class.java) -> {
                TvShowFavoriteViewModel(catalogUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}