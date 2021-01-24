package com.komangss.submissionjetpack.ui.movie.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.komangss.submissionjetpack.business.domain.model.MovieDetail
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
import com.komangss.submissionjetpack.utils.LiveDataTestUtil.getOrAwaitValue
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import com.komangss.submissionjetpack.vo.Resource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var dummyMovie: MovieDetail

    @ExperimentalCoroutinesApi
    private fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) =
        this.testDispatcher.runBlockingTest {
            block()
        }

    @Before
    fun setUp() {
        dummyMovie = DomainModelDataGenerator.getMovieById()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun detailMovie() {
        mainCoroutineRule.runBlockingTest {
            val dummyMovieDetailResult = flowOf(Resource.Success(dummyMovie))
            val repo = mock<CatalogRepository> {
                onBlocking { getMovieById(dummyMovie.id!!) } doReturn dummyMovieDetailResult
            }

            viewModel = MovieDetailViewModel(repo)

            assertEquals(
                dummyMovieDetailResult.first(),
                viewModel.detailMovie(dummyMovie.id!!).getOrAwaitValue()
            )
        }
    }
}