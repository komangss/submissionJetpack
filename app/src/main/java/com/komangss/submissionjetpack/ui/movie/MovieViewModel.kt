package com.komangss.submissionjetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.komangss.submissionjetpack.core.data.CatalogRepository
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.core.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val catalogRepository: CatalogRepository) : ViewModel() {
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    val movieList : LiveData<Resource<List<Movie>>> = liveData {
        emitSource(catalogRepository.getAllMovies().asLiveData())
    }

}