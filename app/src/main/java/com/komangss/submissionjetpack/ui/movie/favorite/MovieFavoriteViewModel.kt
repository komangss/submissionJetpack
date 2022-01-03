package com.komangss.submissionjetpack.ui.movie.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.komangss.submissionjetpack.core.domain.model.Movie
import com.komangss.submissionjetpack.core.domain.usecase.CatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieFavoriteViewModel
@Inject constructor(private val catalogUseCase: CatalogUseCase) : ViewModel() {
    fun getFavoriteMovies(): LiveData<PagedList<Movie>> = catalogUseCase.getFavoriteMovies()
}