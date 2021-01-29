package com.komangss.submissionjetpack.ui.movie.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository

class MovieFavoriteViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    suspend fun getFavoriteMovies() : LiveData<PagedList<Movie>> =
        LivePagedListBuilder(catalogRepository.getFavoriteMovies(), 5).build()
}