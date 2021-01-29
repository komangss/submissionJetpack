package com.komangss.submissionjetpack.ui.tvshow.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.komangss.submissionjetpack.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

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
//    private lateinit var observer: Observer<Resource<TvShowDetail>>

    private lateinit var viewModel: TvShowDetailViewModel
//    private lateinit var dummyTvShow: TvShowDetail

    @Before
    fun setUp() {
//        dummyTvShow = DomainModelDataGenerator.getTvShowById()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun detailTvShow() {
        mainCoroutineRule.runBlockingTest {
//
//            observer = Mockito.mock(Observer::class.java) as Observer<Resource<TvShowDetail>>
//
//            val dummyTvShowResult = flowOf(Resource.Success(dummyTvShow))
//
//            val repo = mock<CatalogRepository> {
//                onBlocking { getTvShowById(dummyTvShow.id!!) } doReturn dummyTvShowResult
//            }
//
//            viewModel = TvShowDetailViewModel(repo)
//
//            Assert.assertEquals(
//                dummyTvShowResult.first(),
//                viewModel.detailTvShow(dummyTvShow.id!!).getOrAwaitValue()
//            )
//
//
//            viewModel.detailTvShow(dummyTvShow.id!!).observeForever(observer)
//
//            verify(observer).onChanged(dummyTvShowResult.first())
        }
    }
}