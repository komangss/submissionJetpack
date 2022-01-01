package com.komangss.submissionjetpack.ui.movie.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.komangss.submissionjetpack.core.data.CatalogRepository
import com.komangss.submissionjetpack.core.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieFavoriteViewModel
@Inject constructor(private val catalogRepository: CatalogRepository) : ViewModel() {
    fun getFavoriteMovies(): LiveData<PagedList<Movie>> = catalogRepository.getFavoriteMovies()
}