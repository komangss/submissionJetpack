package com.komangss.submissionjetpack.ui.movie.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.framework.mapper.CatalogMovieMapper

class MovieFavoriteViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    //    map disini untuk mecegah error saat unit test di repo
    val mapper = CatalogMovieMapper()
    fun getFavoriteMovies(): LiveData<PagedList<Movie>> =
        LivePagedListBuilder(
            catalogRepository.getFavoriteMovies().map { mapper.entityToDomain(it) }, 5
        ).build()
}