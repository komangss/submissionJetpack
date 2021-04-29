package com.komangss.submissionjetpack.ui.movie.detail

import androidx.lifecycle.*
import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(var catalogRepository: CatalogDataSource) : ViewModel() {
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