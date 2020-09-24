package com.komangss.submissionjetpack.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.komangss.submissionjetpack.data.CatalogRepository
import com.komangss.submissionjetpack.di.Injection
import com.komangss.submissionjetpack.ui.movie.MovieViewModel
import com.komangss.submissionjetpack.ui.movie.detail.MovieDetailViewModel

class ViewModelFactory private constructor(private val catalogRepository: CatalogRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideCatalogRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(catalogRepository) as T
            }
            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(catalogRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}