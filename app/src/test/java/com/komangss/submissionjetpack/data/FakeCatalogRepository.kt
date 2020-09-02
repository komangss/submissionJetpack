package com.komangss.submissionjetpack.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.komangss.submissionjetpack.data.source.local.entity.MovieEntity
import com.komangss.submissionjetpack.data.source.remote.CatalogRemoteDataSource
import com.komangss.submissionjetpack.data.source.remote.response.MovieResponse

class FakeCatalogRepository(private val remoteDataSource: CatalogRemoteDataSource) : CatalogDataSource {
    override fun getAllMovies(): LiveData<List<MovieEntity>> {
        val movieResults = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getAllMovies(object : CatalogRemoteDataSource.LoadMoviesCallback {
            override fun onMoviesReceived(movieResponses: List<MovieResponse>) {
                val movieResponseList = ArrayList<MovieEntity>()
                for (response in movieResponses) {
                    val movie = MovieEntity(
                        response.id,
                        response.title,
                        response.director,
                        response.description,
                        response.image,
                        response.releaseDate,
                        response.rating
                    )
                    movieResponseList.add(movie)
                }
                movieResults.postValue(movieResponseList)
            }
        })
        return movieResults
    }
}