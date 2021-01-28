package com.komangss.submissionjetpack.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MovieDetailViewModel (private val catalogRepository: CatalogRepository) : ViewModel() {
    @ExperimentalCoroutinesApi
    fun detailMovie(id : Int) : LiveData<Resource<Movie>> = liveData {
        emitSource(catalogRepository.getMovieById(id).asLiveData())
    }
}