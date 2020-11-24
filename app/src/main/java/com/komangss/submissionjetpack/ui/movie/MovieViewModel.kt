package com.komangss.submissionjetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.launch

class MovieViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    val movieList : LiveData<Resource<List<Movie>>>
        get() = _movieList

    private val _movieList : MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    init {
        viewModelScope.launch {
            _movieList.postValue(catalogRepository.getAllMovies())
        }
    }

}