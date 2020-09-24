package com.komangss.submissionjetpack.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.komangss.submissionjetpack.data.source.local.entity.MovieEntity
import com.komangss.submissionjetpack.data.source.local.entity.TvShowEntity
import com.komangss.submissionjetpack.data.source.remote.CatalogRemoteDataSource
import com.komangss.submissionjetpack.data.source.remote.response.MovieResponse
import com.komangss.submissionjetpack.data.source.remote.response.TvShowResponse

class CatalogRepository private constructor(private val catalogRemoteDataSource: CatalogRemoteDataSource) :
    CatalogDataSource {

    companion object {
        @Volatile
        private var instance: CatalogRepository? = null

        fun getInstance(catalogRemoteDataSource: CatalogRemoteDataSource): CatalogRepository =
            instance ?: synchronized(this) {
                instance ?: CatalogRepository(catalogRemoteDataSource)
            }
    }

    override fun getAllMovies(): LiveData<List<MovieEntity>> {
        val movieResults = MutableLiveData<List<MovieEntity>>()
        catalogRemoteDataSource.getAllMovies(object : CatalogRemoteDataSource.LoadMoviesCallback {
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

    override fun getAllTvShows(): LiveData<List<TvShowEntity>> {
        val tvShowResults = MutableLiveData<List<TvShowEntity>>()
        catalogRemoteDataSource.getAllTvShows(object : CatalogRemoteDataSource.LoadTvShowsCallback {
            override fun onTvShowsReceived(tvShowsResponse: List<TvShowResponse>) {
                val tvShowResponseList = ArrayList<TvShowEntity>()
                for (response in tvShowsResponse) {
                    val tvShow = TvShowEntity (
                        response.id,
                        response.title,
                        response.description,
                        response.image,
                        response.releaseDate,
                        response.rating
                    )
                    tvShowResponseList.add(tvShow)
                }
                tvShowResults.postValue(tvShowResponseList)
            }

        })
        return tvShowResults
    }

    override fun getMovieById(id : Int) : MutableLiveData<MovieEntity> {
        val movieResult = MutableLiveData<MovieEntity>()
        catalogRemoteDataSource.getMovieById(id, object : CatalogRemoteDataSource.LoadMovieByIdCallback {
            override fun onMovieReceived(movieResponse: MovieResponse) {
                val movie = MovieEntity(
                    movieResponse.id,
                    movieResponse.title,
                    movieResponse.director,
                    movieResponse.description,
                    movieResponse.image,
                    movieResponse.releaseDate,
                    movieResponse.rating
                )
                movieResult.postValue(movie)
            }

        })
        return movieResult
    }
}