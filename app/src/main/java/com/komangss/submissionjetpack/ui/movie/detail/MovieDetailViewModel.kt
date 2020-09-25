package com.komangss.submissionjetpack.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository

class MovieDetailViewModel (private val catalogRepository: CatalogRepository) : ViewModel() {
    fun detailMovie(id : Int) : LiveData<Movie> {
        return catalogRepository.getMovieById(id)
    }
}