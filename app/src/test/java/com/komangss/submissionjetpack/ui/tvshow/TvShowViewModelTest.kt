package com.komangss.submissionjetpack.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.komangss.submissionjetpack.core.data.CatalogRepository
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.core.domain.model.TvShow
import com.komangss.submissionjetpack.utils.LiveDataTestUtil.getOrAwaitValue
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import com.komangss.submissionjetpack.utils.datagenerator.TvShowDataGenerator.tvShowDomainList
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

class TvShowViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: TvShowViewModel
    private lateinit var dummyTvShows: List<TvShow>

    @ExperimentalCoroutinesApi
    private fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) =
        this.testDispatcher.runBlockingTest {
            block()
        }

    @Mock
    private lateinit var observer: Observer<Resource<List<TvShow>>>

    @Before
    fun setUp() {
        dummyTvShows = tvShowDomainList
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun getTvShows() {
        mainCoroutineRule.runBlockingTest {
            observer = Mockito.mock(Observer::class.java) as Observer<Resource<List<TvShow>>>
            val dummyTvShowResult = flowOf(Resource.Success(dummyTvShows))
            val repo = mock<CatalogRepository> {
                onBlocking { getAllTvShows() } doReturn dummyTvShowResult
            }

            viewModel = TvShowViewModel(repo)

            assertEquals(
                dummyTvShowResult.first(),
                viewModel.tvShowList.getOrAwaitValue()
            )

            viewModel.tvShowList.observeForever(observer)

            verify(observer).onChanged(dummyTvShowResult.first())
        }
    }
}