package com.komangss.submissionjetpack.favorites.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.komangss.submissionjetpack.core.domain.model.Movie
import com.komangss.submissionjetpack.core.domain.usecase.CatalogUseCase

class MovieFavoriteViewModel constructor(private val catalogUseCase: CatalogUseCase) : ViewModel() {
    fun getFavoriteMovies(): LiveData<PagedList<Movie>> = catalogUseCase.getFavoriteMovies()
}