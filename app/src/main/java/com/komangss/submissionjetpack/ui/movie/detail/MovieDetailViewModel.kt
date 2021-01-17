package com.komangss.submissionjetpack.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.komangss.submissionjetpack.business.domain.model.MovieDetail
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MovieDetailViewModel (private val catalogRepository: CatalogRepository) : ViewModel() {
    @ExperimentalCoroutinesApi
    fun detailMovie(id : Int) : LiveData<Resource<MovieDetail>> = liveData {
        emit(Resource.InProgress)
        emitSource(catalogRepository.getMovieById(id).asLiveData())
    }
}