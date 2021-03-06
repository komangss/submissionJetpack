package com.komangss.submissionjetpack.business.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.mapper.CatalogMovieMapper
import com.komangss.submissionjetpack.framework.mapper.CatalogTvShowMapper
import com.komangss.submissionjetpack.utils.networkBoundResourceTest
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class FakeCatalogRepository
constructor(
    private val catalogRemoteDataSource: CatalogRemoteDataSource,
    private val catalogLocalDataSource: CatalogLocalDataSource,
    private val catalogMovieMapper: CatalogMovieMapper,
    private val catalogTvShowMapper: CatalogTvShowMapper
) : CatalogDataSource {

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getAllMovies(): Flow<Resource<List<Movie>>> {
        return networkBoundResourceTest(
            fetchFromLocal = { catalogLocalDataSource.getAllMovies() },
            shouldFetchFromRemote = { it?.isEmpty() == true },
            fetchFromRemote = { catalogRemoteDataSource.getAllMovies() },
            processRemoteResponse = { },
            saveRemoteData = { movieResultResponse ->
                movieResultResponse.results?.let { movieResponse ->
                    catalogMovieMapper.responsesToEntities(
                        movieResponse
                    )
                }?.let { movieEntities -> catalogLocalDataSource.insertMovies(movieEntities) }
            },
            mapFromCache = { catalogMovieMapper.entitiesToDomains(it) },
            mapFromRemote = { catalogMovieMapper.responsesToDomains(it.results ?: listOf()) }
        )
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getAllTvShows(): Flow<Resource<List<TvShow>>> {
        return networkBoundResourceTest(
            fetchFromRemote = { catalogRemoteDataSource.getAllTvShows() },
            shouldFetchFromRemote = { it?.isEmpty() == true },
            fetchFromLocal = { catalogLocalDataSource.getAllTvShows() },
            processRemoteResponse = {},
            saveRemoteData = { tvShowResultResponse ->
                tvShowResultResponse.results?.let { tvShowResponse ->
                    catalogTvShowMapper.responsesToEntities(
                        tvShowResponse
                    )
                }?.let { tvShowEntities -> catalogLocalDataSource.insertTvShows(tvShowEntities) }
            },
            mapFromCache = { catalogTvShowMapper.entitiesToDomains(it) },
            mapFromRemote = { catalogTvShowMapper.responsesToDomains(it.results ?: listOf()) }
        )
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getMovieById(id: Int): Flow<Resource<Movie>> {
        return networkBoundResourceTest(
            fetchFromRemote = { catalogRemoteDataSource.getMovieById(id) },
            shouldFetchFromRemote = { it == null },
            fetchFromLocal = { catalogLocalDataSource.getMovieById(id) },
            processRemoteResponse = {},
            saveRemoteData = {catalogLocalDataSource.insertMovie(catalogMovieMapper.responseToEntity(it))},
            mapFromCache = { catalogMovieMapper.entityToDomain(it) },
            mapFromRemote = { catalogMovieMapper.responseToDomain(it) },
            shouldCache = { true }
        )
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getTvShowById(id: Int): Flow<Resource<TvShow>> {
        return networkBoundResourceTest(
            fetchFromRemote = { catalogRemoteDataSource.getTvShowById(id) },
            shouldFetchFromRemote = { it == null },
            fetchFromLocal = { catalogLocalDataSource.getTvShowById(id) },
            processRemoteResponse = {},
            saveRemoteData = {catalogLocalDataSource.insertTvShow(catalogTvShowMapper.responseToEntity(it))},
            mapFromCache = { catalogTvShowMapper.entityToDomain(it) },
            mapFromRemote = { catalogTvShowMapper.responseToDomain(it) },
            shouldCache = { true }
        )
    }

    override fun getFavoriteMovies(): LiveData<PagedList<Movie>> {
        return LivePagedListBuilder(
            catalogLocalDataSource.getFavoriteMovies()
                .map { catalogMovieMapper.entityToDomain(it) }, 5
        ).build()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShow>> {
        return LivePagedListBuilder(
            catalogLocalDataSource.getFavoriteTvShows()
                .map { catalogTvShowMapper.entityToDomain(it) }, 5
        ).build()
    }

    override suspend fun updateTvShow(tvShow: TvShow) {
        catalogLocalDataSource.updateTvShow(catalogTvShowMapper.domainToEntity(tvShow))
    }

    override suspend fun updateMovie(movie: Movie) {
        catalogLocalDataSource.updateMovie(catalogMovieMapper.domainToEntity(movie))
    }
}