package com.komangss.submissionjetpack.ui.movie

import androidx.lifecycle.*
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

class MovieViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    val movieList : LiveData<Resource<List<Movie>>> = liveData {
        emitSource(catalogRepository.getAllMovies().asLiveData())
    }

}