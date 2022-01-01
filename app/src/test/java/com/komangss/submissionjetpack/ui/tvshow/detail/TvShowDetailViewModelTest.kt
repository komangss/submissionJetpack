package com.komangss.submissionjetpack.ui.tvshow.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.komangss.submissionjetpack.core.data.CatalogRepository
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.core.domain.model.TvShow
import com.komangss.submissionjetpack.utils.LiveDataTestUtil.getOrAwaitValue
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import com.komangss.submissionjetpack.utils.datagenerator.TvShowDataGenerator.tvShowDomain
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

class TvShowDetailViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    private fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) =
        this.testDispatcher.runBlockingTest {
            block()
        }

    @Mock
    private lateinit var observer: Observer<Resource<TvShow>>

    private lateinit var viewModel: TvShowDetailViewModel
    private lateinit var dummyTvShow: TvShow

    @Before
    fun setUp() {
        dummyTvShow = tvShowDomain
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun detailTvShow() {
        mainCoroutineRule.runBlockingTest {
            observer = Mockito.mock(Observer::class.java) as Observer<Resource<TvShow>>

            val dummyTvShowResult = flowOf(Resource.Success(dummyTvShow))

            val repo = mock<CatalogRepository> {
                onBlocking { getTvShowById(dummyTvShow.id) } doReturn dummyTvShowResult
            }

            viewModel = TvShowDetailViewModel(repo)

            viewModel.tvShow.observeForever(observer)
            viewModel.setTvShowId(dummyTvShow.id)

            assertEquals(
                dummyTvShowResult.first(),
                viewModel.tvShow.getOrAwaitValue()
            )


            verify(observer).onChanged(dummyTvShowResult.first())
        }
    }
}