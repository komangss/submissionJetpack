package com.komangss.submissionjetpack.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.komangss.submissionjetpack.data.CatalogRepository
import com.komangss.submissionjetpack.data.source.local.entity.MovieEntity

class MovieDetailViewModel (private val catalogRepository: CatalogRepository) : ViewModel() {
    fun detailMovie(id : Int) : LiveData<MovieEntity> {
        return catalogRepository.getMovieById(id)
    }
}