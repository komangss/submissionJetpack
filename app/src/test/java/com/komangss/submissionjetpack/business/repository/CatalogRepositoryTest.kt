package com.komangss.submissionjetpack.business.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.framework.network.mappers.NetworkMapper
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
    private val networkMapper = NetworkMapper()
    private val catalogRepository = FakeCatalogRepository(remote, networkMapper)

    private val movieResponses = ResponseDataGenerator.generateDummyMovies()
    private val tvShowResponses = ResponseDataGenerator.generateDummyTvShows()

    private val dummyMovieId = movieResponses[0].id
    private val dummyTvShowId = tvShowResponses[0].id

    private val movieResponse = ResponseDataGenerator.getMovieById(dummyMovieId)
    private val tvShowResponse = ResponseDataGenerator.getTvShowById(dummyTvShowId)

    @Test
    fun getAllMovies() {
        doAnswer { movieResponses }.`when`(remote).getAllMovies(any())

        val movieList = LiveDataTestUtil.getValue(catalogRepository.getAllMovies())
        verify(remote).getAllMovies(any())
        Assert.assertNotNull(movieList)
        Assert.assertEquals(movieResponses.size.toLong(), movieList.size.toLong())
    }

    @Test
    fun getAllTvShows() {
        doAnswer { tvShowResponses }.`when`(remote).getAllTvShows(any())

        val tvShowResults = LiveDataTestUtil.getValue(catalogRepository.getAllTvShows())
        verify(remote).getAllTvShows(any())
        Assert.assertNotNull(tvShowResults)
        Assert.assertEquals(tvShowResponses.size.toLong(), tvShowResults.size.toLong())
    }

    @Test
    fun getMovieById() {
        doAnswer { movieResponse }.`when`(remote).getMovieById(eq(dummyMovieId), any())

        val movieResult = LiveDataTestUtil.getValue(catalogRepository.getMovieById(dummyMovieId))

        verify(remote, times(1)).getMovieById(eq(dummyMovieId), any())

        Assert.assertNotNull(movieResult)
        Assert.assertEquals(movieResult.id, dummyMovieId)
    }


    @Test
    fun getTvShowById() {
        doAnswer { tvShowResponse }.`when`(remote).getTvShowById(eq(dummyTvShowId), any())

        val tvShowResult = LiveDataTestUtil.getValue(catalogRepository.getTvShowById(dummyTvShowId))

        verify(remote, times(1)).getTvShowById(eq(dummyTvShowId), any())

        Assert.assertNotNull(tvShowResult)
        Assert.assertEquals(tvShowResult.id, dummyTvShowId)
    }
}