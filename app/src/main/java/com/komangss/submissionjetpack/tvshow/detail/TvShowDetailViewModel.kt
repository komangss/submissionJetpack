package com.komangss.submissionjetpack.tvshow.detail

import androidx.lifecycle.*
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.core.domain.model.TvShow
import com.komangss.submissionjetpack.core.domain.usecase.CatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject


@HiltViewModel
class TvShowDetailViewModel
@Inject constructor(private val catalogUseCase: CatalogUseCase) : ViewModel() {
    private val id: MutableLiveData<Int> = MutableLiveData()

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    val tvShow: LiveData<Resource<TvShow>> = id.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(catalogUseCase.getTvShowById(id).asLiveData())
        }
    }

    suspend fun setFavorite(tvShow: TvShow) {
        catalogUseCase.updateTvShow(tvShow)
    }

    fun setTvShowId(newId: Int) {
        id.postValue(newId)
    }
}