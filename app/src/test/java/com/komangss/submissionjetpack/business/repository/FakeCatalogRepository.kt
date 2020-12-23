package com.komangss.submissionjetpack.business.repository

import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.mapper.MapperInterface
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.utils.networkBoundResourceNoResultInProgress
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow


class FakeCatalogRepository
constructor(
    private val catalogRemoteDataSource : CatalogRemoteDataSource,
    private val catalogLocalDataSource: CatalogLocalDataSource,
    private val catalogMovieMapper: MapperInterface<Movie, MovieEntity, MovieResponse>
) : CatalogDataSource {

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun getAllMovies(): Flow<Resource<List<Movie>>> {
        return networkBoundResourceNoResultInProgress(
            fetchFromLocal = { catalogLocalDataSource.getAllMovies() },
            shouldFetchFromRemote = { it == null},
            fetchFromRemote = { catalogRemoteDataSource.getAllMovies()},
            processRemoteResponse = { },
            saveRemoteData = {
                catalogLocalDataSource.insertMovies(catalogMovieMapper.responsesToEntities(it.results))
            },
            mapFromCache = {catalogMovieMapper.entitiesToDomains(it)}
        )
    }
}