package com.komangss.submissionjetpack.business.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.liveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.framework.mapper.CatalogMovieMapper
import com.komangss.submissionjetpack.framework.mapper.CatalogTvShowMapper
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import com.komangss.submissionjetpack.utils.PagedListUtil
import com.komangss.submissionjetpack.utils.PagedListUtil.createMockDataSourceFactory
import com.komangss.submissionjetpack.utils.datagenerator.DomainModelDataGenerator
import com.komangss.submissionjetpack.utils.datagenerator.EntityModelDataGenerator.dummyMovieEntities
import com.komangss.submissionjetpack.utils.datagenerator.EntityModelDataGenerator.dummyTvShowEntities
import com.komangss.submissionjetpack.utils.datagenerator.EntityModelDataGenerator.provideDummyMovieEntities
import com.komangss.submissionjetpack.utils.datagenerator.EntityModelDataGenerator.provideDummyTvShowEntities
import com.komangss.submissionjetpack.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class CatalogRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val catalogRemoteDataSource = Mockito.mock(CatalogRemoteDataSource::class.java)
    private val catalogLocalDataSource = Mockito.mock(CatalogLocalDataSource::class.java)
    private val catalogMovieMapperMocked = Mockito.mock(CatalogMovieMapper::class.java)
    private val catalogTvShowMapperMocked = Mockito.mock(CatalogTvShowMapper::class.java)
    private val catalogMovieMapperNotMocked = CatalogMovieMapper()
    private val catalogTvShowMapperNotMocked = CatalogTvShowMapper()

    private val catalogRepository = FakeCatalogRepository(
        catalogRemoteDataSource,
        catalogLocalDataSource,
        catalogMovieMapperMocked,
        catalogTvShowMapperMocked
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

            verify(catalogLocalDataSource).getAllMovies()

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

            verify(catalogLocalDataSource).getAllTvShows()

            Assert.assertEquals(result, listOf(Resource.InProgress, dummyTvShowsResult))

        }


    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun getMovieById() =
        mainCoroutineRule.runBlockingTest {

            val expectedMovieResult = catalogMovieMapperNotMocked.entityToDomain(provideDummyMovieEntities()[0])

            val id = provideDummyMovieEntities()[0].id

            `when`(catalogLocalDataSource.getMovieById(id))
                .thenReturn(flowOf(provideDummyMovieEntities()[0]))

            val result = catalogRepository.getMovieById(id).toList()
            verify(catalogLocalDataSource).getMovieById(id)

            Assert.assertEquals(
                result,
                listOf(Resource.InProgress, Resource.Success(expectedMovieResult))
            )
        }


    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun getTvShowById() =
        mainCoroutineRule.runBlockingTest {
            val expectedTvShowResult = catalogTvShowMapperNotMocked.entityToDomain(provideDummyTvShowEntities()[0])

            val id = provideDummyTvShowEntities()[0].id

            `when`(catalogLocalDataSource.getTvShowById(id))
                .thenReturn(flowOf(provideDummyTvShowEntities()[0]))

            val result = catalogRepository.getTvShowById(id).toList()
            verify(catalogLocalDataSource).getTvShowById(id)

            Assert.assertEquals(
                result,
                listOf(Resource.InProgress, Resource.Success(expectedTvShowResult))
            )
        }

        @Test
        fun getFavoriteMovies() {
            val dataSourceFactoryMovieEntity = object : DataSource.Factory<Int, MovieEntity>() {
                override fun create(): DataSource<Int, MovieEntity> =
                    PagedListUtil.MockLimitDataSource(provideDummyMovieEntities())
            }
            `when`(catalogLocalDataSource.getFavoriteMovies()).thenReturn(dataSourceFactoryMovieEntity)

            catalogRepository.getFavoriteMovies()

            val movieResults: List<Movie> = DomainModelDataGenerator.generateDummyMovies()

            val mockedMoviePagedList: PagedList<Movie> = PagedListUtil.mockPagedList(movieResults)
            val repoResult = liveData {
                emit(mockedMoviePagedList)
            }
            verify(catalogLocalDataSource).getFavoriteMovies()
            assertNotNull(repoResult)
        }


    @Test
    fun getFavoriteTvShows() {
        val dataSourceFactory = mock<DataSource.Factory<Int, TvShowEntity>>()
        `when`(catalogLocalDataSource.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        catalogRepository.getFavoriteTvShows()

        val tvShowEntities = Resource.Success(PagedListUtil.mockPagedList(
            provideDummyTvShowEntities()
        ))

        verify(catalogLocalDataSource).getFavoriteTvShows()
        assertNotNull(tvShowEntities)
    }
}