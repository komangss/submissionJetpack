package com.komangss.submissionjetpack.ui.tvshow.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.framework.mapper.CatalogTvShowMapper

class TvShowFavoriteViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    //    map disini untuk mecegah error saat unit test di repo
    val mapper = CatalogTvShowMapper()
    fun getFavoriteTvShows(): LiveData<PagedList<TvShow>> {
        return LivePagedListBuilder(
            catalogRepository.getFavoriteTvShows().map { mapper.entityToDomain(it) }, 5
        ).build()
    }
}