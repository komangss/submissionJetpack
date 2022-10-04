package com.komangss.submissionjetpack.favorites.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.komangss.submissionjetpack.core.domain.model.TvShow
import com.komangss.submissionjetpack.core.domain.usecase.CatalogUseCase

class TvShowFavoriteViewModel constructor(private val catalogUseCase: CatalogUseCase) :
    ViewModel() {
    fun getFavoriteTvShows(): LiveData<PagedList<TvShow>> = catalogUseCase.getFavoriteTvShows()

}