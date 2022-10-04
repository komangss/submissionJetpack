package com.komangss.submissionjetpack.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.core.domain.model.Movie
import com.komangss.submissionjetpack.core.domain.model.TvShow
import com.komangss.submissionjetpack.core.domain.repository.ICatalogRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatalogInteractor @Inject constructor(private val catalogRepository: ICatalogRepository) :
    CatalogUseCase {
    override suspend fun getAllMovies(): Flow<Resource<List<Movie>>> {
        return catalogRepository.getAllMovies()
    }

    override suspend fun getAllTvShows(): Flow<Resource<List<TvShow>>> {
        return catalogRepository.getAllTvShows()
    }

    override suspend fun updateTvShow(tvShow: TvShow) {
        return catalogRepository.updateTvShow(tvShow)
    }

    override suspend fun updateMovie(movie: Movie) {
        return catalogRepository.updateMovie(movie)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getMovieById(id: Int): Flow<Resource<Movie>> {
        return catalogRepository.getMovieById(id)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getTvShowById(id: Int): Flow<Resource<TvShow>> {
        return catalogRepository.getTvShowById(id)
    }

    override fun getFavoriteMovies(): LiveData<PagedList<Movie>> {
        return catalogRepository.getFavoriteMovies()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShow>> {
        return catalogRepository.getFavoriteTvShows()
    }
}