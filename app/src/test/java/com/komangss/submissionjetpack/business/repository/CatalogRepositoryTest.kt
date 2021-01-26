package com.komangss.submissionjetpack.business.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.framework.mapper.CatalogMovieMapper
import com.komangss.submissionjetpack.framework.mapper.CatalogTvShowMapper
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import com.komangss.submissionjetpack.utils.datagenerator.DomainModelDataGenerator
import com.komangss.submissionjetpack.utils.datagenerator.EntityModelDataGenerator.dummyMovieEntities
import com.komangss.submissionjetpack.utils.datagenerator.EntityModelDataGenerator.dummyTvShowEntities
import com.komangss.submissionjetpack.utils.datagenerator.ResponseDataGenerator.movieDetailResponseToDomain
import com.komangss.submissionjetpack.utils.datagenerator.ResponseDataGenerator.provideDummyMovieApiResponseSuccess
import com.komangss.submissionjetpack.utils.datagenerator.ResponseDataGenerator.provideDummyTvShowApiResponseSuccess
import com.komangss.submissionjetpack.utils.datagenerator.ResponseDataGenerator.tvShowDetailResponseToDomain
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
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

            val dummyMoviesResult = flowOf(
                Resource.Success(DomainModelDataGenerator.generateDummyMovies())
            ).first()

            `when`(catalogLocalDataSource.getAllMovies())
                .thenReturn(dummyMovieEntities())

            val result = catalogRepository.getAllMovies().toList()

            Assert.assertEquals(result, listOf(Resource.InProgress, dummyMoviesResult))

        }


    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun getAllTvShows() =
        mainCoroutineRule.runBlockingTest {

            val dummyTvShowsResult = flowOf(
                Resource.Success(DomainModelDataGenerator.generateDummyTvShows())
            ).first()

            `when`(catalogLocalDataSource.getAllTvShows())
                .thenReturn(dummyTvShowEntities())

            val result = catalogRepository.getAllTvShows().toList()

            Assert.assertEquals(result, listOf(Resource.InProgress, dummyTvShowsResult))

        }


    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun getMovieById() =
        mainCoroutineRule.runBlockingTest {

            val methodResult =
                Resource.Success(
                    movieDetailResponseToDomain(provideDummyMovieApiResponseSuccess().value)
                )

            val movieApiResponseResultSuccess = flowOf(
                provideDummyMovieApiResponseSuccess()
            )

            `when`(provideDummyMovieApiResponseSuccess().value.id?.let { id ->
                catalogRemoteDataSource.getMovieById(
                    id
                )
            })
                .thenReturn(movieApiResponseResultSuccess)

            val result = provideDummyMovieApiResponseSuccess().value.id?.let { id ->
                catalogRepository.getMovieById(
                    id
                ).toList()
            }

            Assert.assertEquals(result, listOf(Resource.InProgress, methodResult))


        }


    @ExperimentalCoroutinesApi
    @Test
    fun getTvShowById() =
    mainCoroutineRule.runBlockingTest {

        val methodResult =
            Resource.Success(
                tvShowDetailResponseToDomain(provideDummyTvShowApiResponseSuccess().value)
            )

        val tvShowApiResponseResultSuccess = flowOf(
            provideDummyTvShowApiResponseSuccess()
        )

        `when`(provideDummyTvShowApiResponseSuccess().value.id?.let { id ->
            catalogRemoteDataSource.getTvShowById(
                id
            )
        })
            .thenReturn(tvShowApiResponseResultSuccess)

        val result = provideDummyTvShowApiResponseSuccess().value.id?.let { id ->
            catalogRepository.getTvShowById(
                id
            ).toList()
        }

        Assert.assertEquals(result, listOf(Resource.InProgress, methodResult))


    }
}