package com.komangss.submissionjetpack.ui.movie.detail

import androidx.lifecycle.*
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.core.domain.model.Movie
import com.komangss.submissionjetpack.core.domain.usecase.CatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
@Inject constructor(
    private val catalogUseCase: CatalogUseCase
) : ViewModel() {
    private val id: MutableLiveData<Int> = MutableLiveData()

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    val movie: LiveData<Resource<Movie>> = id.switchMap { id ->
        liveData<Resource<Movie>>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(catalogUseCase.getMovieById(id).asLiveData())
        }
    }

    suspend fun setFavorite(movie: Movie) {
        catalogUseCase.updateMovie(movie)
    }

    fun setMovieId(newId: Int) {
        id.postValue(newId)
    }
}