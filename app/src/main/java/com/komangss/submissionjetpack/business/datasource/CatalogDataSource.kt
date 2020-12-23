package com.komangss.submissionjetpack.business.datasource

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.flow.Flow

interface CatalogDataSource {
    suspend fun getAllMovies() : Flow<Resource<List<Movie>>>

//    fun getAllTvShows() : LiveData<List<TvShowEntity>>
//
//    fun getMovieById(id : Int) : LiveData<Movie>
}