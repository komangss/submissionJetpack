package com.komangss.submissionjetpack.ui.movie.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.komangss.submissionjetpack.business.domain.model.MovieDetail
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import com.komangss.submissionjetpack.vo.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

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
            val dummyMovieDetailResult = flow {
                emit(Resource.Success(dummyMovie))
            }
            val repo = mock<CatalogRepository> {
                onBlocking { getMovieById(dummyMovie.id!!) } doReturn dummyMovieDetailResult
            }

            viewModel = MovieDetailViewModel(repo)


            assertEquals(
                Resource.InProgress,
                viewModel.detailMovie(dummyMovie.id!!).getOrAwaitValue()
            )
        }
    }


    /* Copyright 2019 Google LLC.
       SPDX-License-Identifier: Apache-2.0 */
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}