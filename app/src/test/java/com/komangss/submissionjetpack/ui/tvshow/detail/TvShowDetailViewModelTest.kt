package com.komangss.submissionjetpack.ui.tvshow.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.komangss.submissionjetpack.business.domain.model.TvShowDetail
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.utils.datagenerator.DomainModelDataGenerator
import com.komangss.submissionjetpack.utils.LiveDataTestUtil.getOrAwaitValue
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import com.komangss.submissionjetpack.vo.Resource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

    private lateinit var viewModel: TvShowDetailViewModel
    private lateinit var dummyTvShow: TvShowDetail

    @Before
    fun setUp() {
        dummyTvShow = DomainModelDataGenerator.getTvShowById()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun detailTvShow() {
        mainCoroutineRule.runBlockingTest {
            val dummyTvShowResult = flowOf(Resource.Success(dummyTvShow))

            val repo = mock<CatalogRepository> {
                onBlocking { getTvShowById(dummyTvShow.id!!) } doReturn dummyTvShowResult
            }

            viewModel = TvShowDetailViewModel(repo)

            Assert.assertEquals(
                dummyTvShowResult.first(),
                viewModel.detailTvShow(dummyTvShow.id!!).getOrAwaitValue()
            )
        }
    }
}