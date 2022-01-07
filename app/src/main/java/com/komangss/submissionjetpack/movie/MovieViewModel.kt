package com.komangss.submissionjetpack.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.core.domain.model.Movie
import com.komangss.submissionjetpack.core.domain.usecase.CatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val catalogUseCase: CatalogUseCase) : ViewModel() {
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    val movieList: LiveData<Resource<List<Movie>>> = liveData {
        emitSource(catalogUseCase.getAllMovies().asLiveData())
    }

}