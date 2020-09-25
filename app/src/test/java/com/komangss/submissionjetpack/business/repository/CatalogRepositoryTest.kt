package com.komangss.submissionjetpack.business.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.framework.network.mappers.MovieNetworkMapper
import com.komangss.submissionjetpack.utils.ResponseDataGenerator
import com.komangss.submissionjetpack.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Test

import org.junit.Rule
import org.mockito.Mockito.mock

class CatalogRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(CatalogRemoteDataSource::class.java)
    private val networkMapper = MovieNetworkMapper()
    private val catalogRepository = FakeCatalogRepository(remote, networkMapper)

    private val movieResponses = ResponseDataGenerator.generateDummyMovies()
    private val tvShowResponse = ResponseDataGenerator.generateDummyTvShows()

    private val movieId = movieResponses[0].id

    private val movieResponse = ResponseDataGenerator.getMovieById(movieId)

    @Test
    fun getAllMovies() {
        doAnswer { invocation ->
            (invocation.arguments[0] as CatalogRemoteDataSource.LoadMoviesCallback)
                .onMoviesReceived(movieResponses)
            null
        }.`when`(remote).getAllMovies(any())

        val movieList = LiveDataTestUtil.getValue(catalogRepository.getAllMovies())
        verify(remote).getAllMovies(any())
        Assert.assertNotNull(movieList)
        Assert.assertEquals(movieResponses.size.toLong(), movieList.size.toLong())
    }

    @Test
    fun getAllTvShows() {
        doAnswer { invocation ->
            (invocation.arguments[0] as CatalogRemoteDataSource.LoadTvShowsCallback)
                .onTvShowsReceived(tvShowResponse)
            null
        }.`when`(remote).getAllTvShows(any())

        val tvShowEntities = LiveDataTestUtil.getValue(catalogRepository.getAllTvShows())
        verify(remote).getAllTvShows(any())
        Assert.assertNotNull(tvShowEntities)
        Assert.assertEquals(tvShowResponse.size.toLong(), tvShowEntities.size.toLong())
    }

    @Test
    fun getMovieById() {
        doAnswer { invocation ->
            if (movieResponse != null) {
                (invocation.arguments[1] as CatalogRemoteDataSource.LoadMovieByIdCallback)
                    .onMovieReceived(movieResponse)
            }
            null
        }.`when`(remote).getMovieById(eq(movieId), any())

        val movieEntity = LiveDataTestUtil.getValue(catalogRepository.getMovieById(movieId))

        verify(remote, times(1)).getMovieById(eq(movieId), any())

        Assert.assertNotNull(movieEntity)
        Assert.assertEquals(movieEntity.id, movieId)
    }
}