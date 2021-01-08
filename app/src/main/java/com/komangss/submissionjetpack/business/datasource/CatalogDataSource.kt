package com.komangss.submissionjetpack.business.datasource

import androidx.lifecycle.LiveData
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.flow.Flow

interface CatalogDataSource {
    suspend fun getAllMovies() : Flow<Resource<List<Movie>>>

    fun getAllTvShows() : Flow<Resource<List<TvShow>>>

//    fun getMovieById(id : Int) : LiveData<Movie>

//    fun getTvShowById(id : Int) : LiveData<TvShow>
}