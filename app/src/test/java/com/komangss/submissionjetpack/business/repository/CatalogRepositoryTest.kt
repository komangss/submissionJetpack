package com.komangss.submissionjetpack.business.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.core.data.source.local.CatalogLocalDataSource
import com.komangss.submissionjetpack.core.data.source.local.entity.MovieEntity
import com.komangss.submissionjetpack.core.data.source.local.entity.TvShowEntity
import com.komangss.submissionjetpack.core.data.source.remote.CatalogRemoteDataSource
import com.komangss.submissionjetpack.core.data.source.remote.network.ApiResponse
import com.komangss.submissionjetpack.core.utils.mapper.CatalogMovieMapper
import com.komangss.submissionjetpack.core.utils.mapper.CatalogTvShowMapper
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import com.komangss.submissionjetpack.utils.PagedListUtil
import com.komangss.submissionjetpack.utils.datagenerator.MovieDataGenerator
import com.komangss.submissionjetpack.utils.datagenerator.MovieDataGenerator.movieDomain
import com.komangss.submissionjetpack.utils.datagenerator.MovieDataGenerator.movieEntity
import com.komangss.submissionjetpack.utils.datagenerator.MovieDataGenerator.movieEntityList
import com.komangss.submissionjetpack.utils.datagenerator.TvShowDataGenerator
import com.komangss.submissionjetpack.utils.datagenerator.TvShowDataGenerator.tvShowDomain
import com.komangss.submissionjetpack.utils.datagenerator.TvShowDataGenerator.tvShowDomainList
import com.komangss.submissionjetpack.utils.datagenerator.TvShowDataGenerator.tvShowEntity
import com.komangss.submissionjetpack.utils.datagenerator.TvShowDataGenerator.tvShowEntityList
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.times


class CatalogRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val catalogRemoteDataSource = Mockito.mock(CatalogRemoteDataSource::class.java)
    private val catalogLocalDataSource = Mockito.mock(CatalogLocalDataSource::class.java)
    private val catalogMovieMapper = CatalogMovieMapper()
    private val catalogTvShowMapper = CatalogTvShowMapper()

    private val catalogRepository = FakeICatalogRepository(
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

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun `get movie from local when local data movie is available`() =
        mainCoroutineRule.runBlockingTest {

            val dummyMoviesResult = flowOf(
                Resource.Success(MovieDataGenerator.movieDomainList)
            ).first()

            val dummyMoviesLocalResult = flow {
                emit(movieEntityList)
            }

            `when`(catalogLocalDataSource.getAllMovies())
                .thenReturn(dummyMoviesLocalResult)

            val result = catalogRepository.getAllMovies().toList()

            verify(catalogLocalDataSource).getAllMovies()

            Assert.assertEquals(result, listOf(Resource.InProgress, dummyMoviesResult))
        }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun `get movie from remote when local data movie is empty`() =
        mainCoroutineRule.runBlockingTest {

            val dummyMoviesResult = flowOf(
                Resource.Success(MovieDataGenerator.movieDomainList)
            ).first()

            val dummyMoviesLocalResultFirst = flow<List<MovieEntity>> {
                emit(listOf())
            }

            val dummyMoviesLocalResultSecond = flow {
                emit(movieEntityList)
            }

            val dummyMovieResponses = flowOf(
                ApiResponse.Success(
                    MovieDataGenerator.movieResultResponse
                )
            )

            `when`(catalogLocalDataSource.getAllMovies())
                .thenReturn(dummyMoviesLocalResultFirst, dummyMoviesLocalResultSecond)

            `when`(catalogRemoteDataSource.getAllMovies())
                .thenReturn(dummyMovieResponses)

            `when`(catalogLocalDataSource.insertMovies(any())).thenAnswer { }

            val result = catalogRepository.getAllMovies().toList()

            verify(catalogLocalDataSource, times(2)).getAllMovies()
            verify(catalogLocalDataSource).insertMovies(any())
            verify(catalogRemoteDataSource).getAllMovies()

            Assert.assertEquals(result, listOf(Resource.InProgress, dummyMoviesResult))
        }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun `get tv show from local when local data tv show is available`() =
        mainCoroutineRule.runBlockingTest {

            val dummyTvShowsResult = flowOf(
                Resource.Success(tvShowDomainList)
            ).first()

            val dummyTvShowsLocalResult = flow {
                emit(tvShowEntityList)
            }

            `when`(catalogLocalDataSource.getAllTvShows())
                .thenReturn(dummyTvShowsLocalResult)

            val result = catalogRepository.getAllTvShows().toList()

            verify(catalogLocalDataSource).getAllTvShows()

            Assert.assertEquals(result, listOf(Resource.InProgress, dummyTvShowsResult))
        }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun `get tv show from remote when local data tv show is empty`() =
        mainCoroutineRule.runBlockingTest {

            val dummyTvShowsResult = flowOf(
                Resource.Success(tvShowDomainList)
            ).first()

            val dummyTvShowsLocalResultFirst = flow<List<TvShowEntity>> {
                emit(listOf())
            }

            val dummyTvShowsLocalResultSecond = flow {
                emit(tvShowEntityList)
            }

            val dummyTvShowResponses = flowOf(
                ApiResponse.Success(
                    TvShowDataGenerator.tvShowResultResponse
                )
            )

            `when`(catalogLocalDataSource.getAllTvShows())
                .thenReturn(dummyTvShowsLocalResultFirst, dummyTvShowsLocalResultSecond)

            `when`(catalogRemoteDataSource.getAllTvShows())
                .thenReturn(dummyTvShowResponses)

            `when`(catalogLocalDataSource.insertTvShows(any())).thenAnswer { }

            val result = catalogRepository.getAllTvShows().toList()

            verify(catalogLocalDataSource, times(2)).getAllTvShows()
            verify(catalogLocalDataSource).insertTvShows(any())
            verify(catalogRemoteDataSource).getAllTvShows()

            Assert.assertEquals(result, listOf(Resource.InProgress, dummyTvShowsResult))
        }


    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun getMovieById() =
        mainCoroutineRule.runBlockingTest {

            val expectedMovieResult =
                catalogMovieMapper.entityToDomain(movieEntity)

            val id = movieEntity.id

            `when`(catalogLocalDataSource.getMovieById(id))
                .thenReturn(flowOf(movieEntity))

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
            val expectedTvShowResult =
                catalogTvShowMapper.entityToDomain(tvShowEntity)

            val id = tvShowEntity.id

            `when`(catalogLocalDataSource.getTvShowById(id))
                .thenReturn(flowOf(tvShowEntity))

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
                PagedListUtil.MockLimitDataSource(movieEntityList)
        }
        `when`(catalogLocalDataSource.getFavoriteMovies()).thenReturn(dataSourceFactoryMovieEntity)

        val result = catalogRepository.getFavoriteMovies()

        verify(catalogLocalDataSource).getFavoriteMovies()
        assertNotNull(result)
    }


    @Test
    fun getFavoriteTvShows() {
        val dataSourceFactoryTvShowEntity = object : DataSource.Factory<Int, TvShowEntity>() {
            override fun create(): DataSource<Int, TvShowEntity> =
                PagedListUtil.MockLimitDataSource(tvShowEntityList)
        }
        `when`(catalogLocalDataSource.getFavoriteTvShows()).thenReturn(dataSourceFactoryTvShowEntity)
        catalogRepository.getFavoriteTvShows()

        val result = catalogRepository.getFavoriteTvShows()

        verify(catalogLocalDataSource, times(2)).getFavoriteTvShows()
        assertNotNull(result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test update movie in catalog local data source`() =
        mainCoroutineRule.runBlockingTest {
            catalogRepository.updateMovie(movieDomain)
            verify(catalogLocalDataSource).updateMovie(any())
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `test update Tv Show in catalog local data source`() =
        mainCoroutineRule.runBlockingTest {
            catalogRepository.updateTvShow(tvShowDomain)
            verify(catalogLocalDataSource).updateTvShow(any())
        }
}