package com.komangss.submissionjetpack.ui.tvshow.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.business.repository.CatalogRepository

class TvShowFavoriteViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    suspend fun getFavoriteTvShows() : LiveData<PagedList<TvShow>> =
        LivePagedListBuilder(catalogRepository.getFavoriteTvShows(), 5).build()
}