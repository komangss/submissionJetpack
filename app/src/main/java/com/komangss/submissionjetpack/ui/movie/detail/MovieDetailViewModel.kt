package com.komangss.submissionjetpack.ui.movie.detail

import androidx.lifecycle.*
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MovieDetailViewModel (private val catalogRepository: CatalogRepository) : ViewModel() {
    private val id : MutableLiveData<Int> = MutableLiveData()

    @ExperimentalCoroutinesApi
    val movie : LiveData<Resource<Movie>> = id.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(catalogRepository.getMovieById(id).asLiveData())
        }
    }

    suspend fun setFavorite(movie: Movie) {
        catalogRepository.setMovieFavorite(movie)
    }

    fun setMovieId(newId : Int) {
        id.postValue(newId)
    }
}