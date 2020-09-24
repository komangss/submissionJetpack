package com.komangss.submissionjetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.komangss.submissionjetpack.data.CatalogRepository
import com.komangss.submissionjetpack.data.source.local.entity.MovieEntity

class MovieViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    fun getMovies() : LiveData<List<MovieEntity>> =
        catalogRepository.getAllMovies()
}