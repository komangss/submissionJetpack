package com.komangss.submissionjetpack.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.business.repository.CatalogRepository

class TvShowViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    fun getTvShows() : LiveData<List<TvShow>> =
        catalogRepository.getAllTvShows()
}