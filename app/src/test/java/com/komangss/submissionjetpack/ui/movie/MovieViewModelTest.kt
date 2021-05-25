package com.komangss.submissionjetpack.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.utils.LiveDataTestUtil.getOrAwaitValue
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import com.komangss.submissionjetpack.utils.datagenerator.MovieDataGenerator.movieDomainList
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

class MovieViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MovieViewModel
    private lateinit var dummyMovies: List<Movie>

    @ExperimentalCoroutinesApi
    private fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) =
        this.testDispatcher.runBlockingTest {
            block()
        }

    @Mock
    private lateinit var observer: Observer<Resource<List<Movie>>>

    @Before
    fun setUp() {
        dummyMovies = movieDomainList
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun getMovies() {
        mainCoroutineRule.runBlockingTest {
            observer = Mockito.mock(Observer::class.java) as Observer<Resource<List<Movie>>>
            val dummyMovieResult = flowOf(Resource.Success(dummyMovies))
            val repo = mock<CatalogRepository> {
                onBlocking { getAllMovies() } doReturn dummyMovieResult
            }

            viewModel = MovieViewModel(repo)

            assertEquals(
                dummyMovieResult.first(),
                viewModel.movieList.getOrAwaitValue()
            )

            viewModel.movieList.observeForever(observer)

            verify(observer).onChanged(dummyMovieResult.first())
        }
    }
}