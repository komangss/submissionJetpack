package com.komangss.submissionjetpack.data

import androidx.lifecycle.LiveData
import com.komangss.submissionjetpack.data.source.local.entity.MovieEntity

interface CatalogDataSource {
    fun getAllMovies() : LiveData<List<MovieEntity>>
}