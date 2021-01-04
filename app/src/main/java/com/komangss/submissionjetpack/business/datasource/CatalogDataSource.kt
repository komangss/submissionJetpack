package com.komangss.submissionjetpack.business.datasource

import androidx.lifecycle.LiveData
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity

interface CatalogDataSource {
    fun getAllMovies() : LiveData<List<Movie>>

    fun getAllTvShows() : LiveData<List<TvShow>>

    fun getMovieById(id : Int) : LiveData<Movie>

    fun getTvShowById(id : Int) : LiveData<TvShow>
}