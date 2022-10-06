package com.komangss.submissionjetpack.movie.detail

import androidx.lifecycle.*
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.core.domain.model.Movie
import com.komangss.submissionjetpack.core.domain.usecase.CatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
@Inject constructor(
    private val catalogUseCase: CatalogUseCase
) : ViewModel() {
    private val id: MutableLiveData<Int> = MutableLiveData()

    val movie: LiveData<Resource<Movie>> = id.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
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