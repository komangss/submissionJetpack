package com.komangss.submissionjetpack.ui.movie.detail

import androidx.lifecycle.*
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
@Inject constructor(
    private val catalogRepository: CatalogRepository
) : ViewModel() {
    private val id: MutableLiveData<Int> = MutableLiveData()

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    val movie: LiveData<Resource<Movie>> = id.switchMap { id ->
        liveData<Resource<Movie>>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(catalogRepository.getMovieById(id).asLiveData())
        }
    }

    suspend fun setFavorite(movie: Movie) {
        catalogRepository.updateMovie(movie)
    }

    fun setMovieId(newId: Int) {
        id.postValue(newId)
    }
}