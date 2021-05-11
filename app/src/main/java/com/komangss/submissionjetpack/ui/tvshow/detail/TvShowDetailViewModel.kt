package com.komangss.submissionjetpack.ui.tvshow.detail

import androidx.lifecycle.*
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

class TvShowDetailViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    private val id : MutableLiveData<Int> = MutableLiveData()

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    val tvShow : LiveData<Resource<TvShow>> = id.switchMap { id ->
        liveData<Resource<TvShow>>(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(catalogRepository.getTvShowById(id).asLiveData())
        }
    }

    suspend fun setFavorite(tvShow: TvShow) {
        catalogRepository.updateTvShow(tvShow)
    }

    fun setTvShowId(newId : Int) {
        id.postValue(newId)
    }
}