package com.komangss.submissionjetpack.utils

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow

object DomainModelDataGenerator {
    fun generateDummyMovies(): List<Movie> {
        return listOf()
    }

    fun getMovieById(id: Int): Movie? = generateDummyMovies().firstOrNull { id == it.id }

    fun generateDummyTvShows(): List<TvShow> {
        return listOf()
    }

    fun getTvShowById(id: Int) : TvShow? = generateDummyTvShows().firstOrNull { id == it.id }

}