package com.komangss.submissionjetpack.ui.movie.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.framework.mapper.CatalogMovieMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieFavoriteViewModel
@Inject constructor(private val catalogRepository: CatalogRepository) : ViewModel() {
    fun getFavoriteMovies(): LiveData<PagedList<Movie>> = catalogRepository.getFavoriteMovies()
}