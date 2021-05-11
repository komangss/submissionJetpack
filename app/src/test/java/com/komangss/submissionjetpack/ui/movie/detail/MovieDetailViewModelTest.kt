package com.komangss.submissionjetpack.ui.movie.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.utils.LiveDataTestUtil.getOrAwaitValue
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import com.komangss.submissionjetpack.utils.datagenerator.DomainModelDataGenerator
import com.komangss.submissionjetpack.vo.Resource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class MovieDetailViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var dummyMovie: Movie

    @ExperimentalCoroutinesApi
    private fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) =
        this.testDispatcher.runBlockingTest {
            block()
        }

    @Mock
    private lateinit var observer: Observer<Resource<Movie>>

    @Before
    fun setUp() {
        dummyMovie = DomainModelDataGenerator.generateDummyMovies()[0]
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun detailMovie() {
        mainCoroutineRule.runBlockingTest {
            observer = Mockito.mock(Observer::class.java) as Observer<Resource<Movie>>
            val dummyMovieResult = flowOf(Resource.Success(dummyMovie))
            val repo = mock<CatalogRepository> {
                onBlocking { getMovieById(dummyMovie.id) } doReturn dummyMovieResult
            }

            viewModel = MovieDetailViewModel(repo)
            viewModel.movie.observeForever(observer)

            viewModel.setMovieId(dummyMovie.id)

            assertEquals(
                dummyMovieResult.first(),
                viewModel.movie.getOrAwaitValue()
            )

            verify(observer).onChanged(dummyMovieResult.first())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateMovieFavorite() {
        mainCoroutineRule.runBlockingTest {
            val repo = mock<CatalogRepository>()
            viewModel = MovieDetailViewModel(repo)
            viewModel.setFavorite(dummyMovie)

            verify(repo).updateMovie(dummyMovie)
        }
    }
}