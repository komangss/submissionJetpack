package com.komangss.submissionjetpack.ui.movie

import androidx.lifecycle.ViewModel
import com.komangss.submissionjetpack.data.source.local.entity.MovieEntity
import com.komangss.submissionjetpack.utils.DataGenerator

class MovieViewModel : ViewModel() {
    fun getMovies() : List<MovieEntity> = DataGenerator.generateDummyMovies()
}