package com.komangss.submissionjetpack.utils.datagenerator

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieDataGenerator {
    val dummyMovieEntities : Flow<List<MovieEntity>> = flow {
        emit(provideDummyMovieList())
    }

    fun provideDummyMovieList() : ArrayList<MovieEntity> {
        val dummyMovieList : ArrayList<MovieEntity> = ArrayList()
        dummyMovieList.add(MovieEntity(
            10.0,
            5000,
            false,
            "http://example.com",
            123,
            "http://example.com",
            "en-us",
            "dummy title",
            "[1, 2, 3]",
            "dummy title",
            10.0,
            "dummy description",
            "10-10-2020"
        ))
        return dummyMovieList
    }
}