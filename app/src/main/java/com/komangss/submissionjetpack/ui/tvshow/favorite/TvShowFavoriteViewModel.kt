package com.komangss.submissionjetpack.ui.tvshow.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.komangss.submissionjetpack.core.data.CatalogRepository
import com.komangss.submissionjetpack.core.domain.model.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowFavoriteViewModel
@Inject constructor(private val catalogRepository: CatalogRepository) : ViewModel() {
    fun getFavoriteTvShows(): LiveData<PagedList<TvShow>> = catalogRepository.getFavoriteTvShows()

}