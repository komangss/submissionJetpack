package com.komangss.submissionjetpack.business.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.framework.cache.mappers.MovieCacheMapper
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.network.mappers.MovieNetworkMapper
import com.komangss.submissionjetpack.utils.AppExecutors
import com.komangss.submissionjetpack.utils.EntityDataGenerator
import com.komangss.submissionjetpack.utils.ResponseDataGenerator
import com.komangss.submissionjetpack.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Test

import org.junit.Rule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CatalogRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val catalogRemoteDataSource = mock(CatalogRemoteDataSource::class.java)
    private val catalogLocalDataSource = mock(CatalogLocalDataSource::class.java)

    private val networkMapper = MovieNetworkMapper()
    private val cacheMapper = MovieCacheMapper()

    private val appExecutors = mock(AppExecutors::class.java)

    private val catalogRepository = FakeCatalogRepository(
        catalogRemoteDataSource,
        catalogLocalDataSource,
        networkMapper,
        cacheMapper,
        appExecutors
    )

    private val movieResponses = ResponseDataGenerator.generateDummyMovies()
    private val tvShowResponse = ResponseDataGenerator.generateDummyTvShows()

    private val movieId = movieResponses[0].id

    private val movieResponse = ResponseDataGenerator.getMovieById(movieId)

    @Test
    fun getAllMovies() {
        val dummyMovies = MutableLiveData<List<MovieEntity>>()
        dummyMovies.value = EntityDataGenerator.generateDummyMovies()
        `when`(catalogLocalDataSource.getAllMovies()).thenReturn(dummyMovies)

        val movieList = LiveDataTestUtil.getValue(catalogRepository.getAllMovies())
        verify(catalogLocalDataSource).getAllMovies()
        Assert.assertNotNull(movieList)
//        Assert.assertEquals(movieResponses.size, movieList.status)
    }

    @Test
    fun getAllTvShows() {
//        doAnswer { invocation ->
//            (invocation.arguments[0] as CatalogRemoteDataSource.LoadTvShowsCallback)
//                .onTvShowsReceived(tvShowResponse)
//            null
//        }.`when`(catalogRemoteDataSource).getAllTvShows()
//
//        val tvShowEntities = LiveDataTestUtil.getValue(catalogRepository.getAllTvShows())
//        verify(catalogRemoteDataSource).getAllTvShows()
//        Assert.assertNotNull(tvShowEntities)
//        Assert.assertEquals(tvShowResponse.size.toLong(), tvShowEntities.size.toLong())
    }

    @Test
    fun getMovieById() {
        doAnswer { invocation ->
            if (movieResponse != null) {
                (invocation.arguments[1] as CatalogRemoteDataSource.LoadMovieByIdCallback)
                    .onMovieReceived(movieResponse)
            }
            null
        }.`when`(catalogRemoteDataSource).getMovieById(eq(movieId), any())

        val movieEntity = LiveDataTestUtil.getValue(catalogRepository.getMovieById(movieId))

        verify(catalogRemoteDataSource, times(1)).getMovieById(eq(movieId), any())

        Assert.assertNotNull(movieEntity)
        Assert.assertEquals(movieEntity.id, movieId)
    }
}