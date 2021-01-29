package com.komangss.submissionjetpack.ui.tvshow.detail

import androidx.lifecycle.*
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi

class TvShowDetailViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    private val id : MutableLiveData<Int> = MutableLiveData()

    @ExperimentalCoroutinesApi
    val tvShow : LiveData<Resource<TvShow>> = Transformations.switchMap(id) { id ->
        catalogRepository.getTvShowById(id).asLiveData()
    }

    suspend fun setFavorite(tvShow: TvShow) {
        catalogRepository.setTvShowFavorite(tvShow)
    }

    fun setTvShowId(newId : Int) {
        id.postValue(newId)
    }
}