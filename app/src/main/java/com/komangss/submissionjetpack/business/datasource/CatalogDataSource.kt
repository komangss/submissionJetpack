package com.komangss.submissionjetpack.business.datasource

import androidx.lifecycle.LiveData
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.vo.Resource

interface CatalogDataSource {
    fun getAllMovies() : LiveData<Resource<List<MovieEntity>>>

    fun getAllTvShows() : LiveData<List<TvShowEntity>>

    fun getMovieById(id : Int) : LiveData<Movie>
}