package com.komangss.submissionjetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository

class MovieViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    fun getMovies() : LiveData<List<Movie>> =
        catalogRepository.getAllMovies()
}