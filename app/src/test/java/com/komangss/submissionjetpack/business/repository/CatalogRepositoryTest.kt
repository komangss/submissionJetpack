package com.komangss.submissionjetpack.business.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.mapper.CatalogMovieMapper
import com.komangss.submissionjetpack.framework.mapper.CatalogTvShowMapper
import com.komangss.submissionjetpack.utils.LiveDataTestUtil
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import com.komangss.submissionjetpack.utils.datagenerator.MovieDataGenerator
import com.komangss.submissionjetpack.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.mockito.Mockito.`when`
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

import org.mockito.Mockito.mock

class CatalogRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val catalogRemoteDataSource = mock(CatalogRemoteDataSource::class.java)
    private val catalogLocalDataSource = mock(CatalogLocalDataSource::class.java)
    private val catalogMovieMapper = CatalogMovieMapper()
    private val catalogTvShowMapper = CatalogTvShowMapper()

    private val catalogRepository = FakeCatalogRepository(
        catalogRemoteDataSource,
        catalogLocalDataSource,
        catalogMovieMapper,
        catalogTvShowMapper
    )

    private val movieDataGenerator = MovieDataGenerator()

    private val dummyMovieResult: Resource<List<Movie>> =
        Resource.Success(
            catalogMovieMapper.entitiesToDomains(movieDataGenerator.provideDummyMovieList())
        )

    @ExperimentalCoroutinesApi
    private fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) =
        this.testDispatcher.runBlockingTest {
            block()
        }

    //    IF CACHE AVAILABLE
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun `get movie from local when local data movie is available`() =
        mainCoroutineRule.runBlockingTest {
            `when`(catalogLocalDataSource.getAllMovies())
                .thenReturn(movieDataGenerator.dummyMovieEntities)

            val movieListResult =
                LiveDataTestUtil.getValue(catalogRepository.getAllMovies().asLiveData())
            verify(catalogLocalDataSource).getAllMovies()
            Assert.assertNotNull(movieListResult)
            Assert.assertEquals(dummyMovieResult, movieListResult)

        }


    @Test
    fun getAllTvShows() {
//        doAnswer { tvShowResponses }.`when`(remote).getAllTvShows(any())
//
//        val tvShowResults = LiveDataTestUtil.getValue(catalogRepository.getAllTvShows())
//        verify(remote).getAllTvShows(any())
//        Assert.assertNotNull(tvShowResults)
//        Assert.assertEquals(tvShowResponses.size.toLong(), tvShowResults.size.toLong())
    }

    @Test
    fun getMovieById() {
//        doAnswer { movieResponse }.`when`(remote).getMovieById(eq(dummyMovieId), any())

//        val movieResult = LiveDataTestUtil.getValue(catalogRepository.getMovieById(dummyMovieId))
//
//        verify(remote, times(1)).getMovieById(eq(dummyMovieId), any())
//
//        Assert.assertNotNull(movieResult)
//        Assert.assertEquals(movieResult.id, dummyMovieId)
    }


    @Test
    fun getTvShowById() {
//        doAnswer { tvShowResponse }.`when`(remote).getTvShowById(eq(dummyTvShowId), any())
//
//        val tvShowResult = LiveDataTestUtil.getValue(catalogRepository.getTvShowById(dummyTvShowId))
//
//        verify(remote, times(1)).getTvShowById(eq(dummyTvShowId), any())
//
//        Assert.assertNotNull(tvShowResult)
//        Assert.assertEquals(tvShowResult.id, dummyTvShowId)
    }
}