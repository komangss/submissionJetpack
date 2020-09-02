package com.komangss.submissionjetpack.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.komangss.submissionjetpack.data.source.remote.CatalogRemoteDataSource
import com.komangss.submissionjetpack.utils.DataGenerator
import com.komangss.submissionjetpack.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Test

import org.junit.Rule
import org.mockito.Mockito.mock

class CatalogRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(CatalogRemoteDataSource::class.java)
    private val catalogRepository = FakeCatalogRepository(remote)

    private val movieResponses = DataGenerator.generateDummyMovies()
    private val tvShowResponse = DataGenerator.generateDummyTvShows()

    @Test
    fun getAllMovies() {
        doAnswer { invocation ->
            (invocation.arguments[0] as CatalogRemoteDataSource.LoadMoviesCallback)
                .onMoviesReceived(movieResponses)
            null
        }.`when`(remote).getAllMovies(any())

        val movieEntities = LiveDataTestUtil.getValue(catalogRepository.getAllMovies())
        verify(remote).getAllMovies(any())
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(movieResponses.size.toLong(), movieEntities.size.toLong())
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
}