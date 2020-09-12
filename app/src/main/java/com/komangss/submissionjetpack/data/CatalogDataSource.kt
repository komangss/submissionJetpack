package com.komangss.submissionjetpack.data

import androidx.lifecycle.LiveData
import com.komangss.submissionjetpack.data.source.local.entity.MovieEntity
import com.komangss.submissionjetpack.data.source.local.entity.TvShowEntity

interface CatalogDataSource {
    fun getAllMovies() : LiveData<List<MovieEntity>>

    fun getAllTvShows() : LiveData<List<TvShowEntity>>

    fun getMovieById(id : Int) : LiveData<MovieEntity>
}