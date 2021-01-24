package com.komangss.submissionjetpack.ui.tvshow.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.komangss.submissionjetpack.business.domain.model.TvShowDetail
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi

class TvShowDetailViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    @ExperimentalCoroutinesApi
    fun detailTvShow(id : Int) : LiveData<Resource<TvShowDetail>> = liveData {
        emitSource(catalogRepository.getTvShowById(id).asLiveData())
    }
}