package com.komangss.submissionjetpack.ui.tvshow.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class TvShowDetailViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel : TvShowDetailViewModel
    private lateinit var dummyTvShow : TvShow
    private val catalogRepository = Mockito.mock(CatalogRepository::class.java)

    @Before
    fun setUp() {
        viewModel = TvShowDetailViewModel(catalogRepository)
        dummyTvShow = DomainModelDataGenerator.generateDummyTvShows()[0]
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun detailTvShow() {
        val mutableTvShow = MutableLiveData<TvShow>()
        mutableTvShow.value = DomainModelDataGenerator.getTvShowById(dummyTvShow.id)
        Mockito.`when`(catalogRepository.getTvShowById(dummyTvShow.id)).thenReturn(mutableTvShow)
        val observer: Observer<TvShow> = Mockito.mock(Observer::class.java) as Observer<TvShow>
        viewModel.getTvShowById(dummyTvShow.id).observeForever(observer)
        Mockito.verify(catalogRepository).getTvShowById(dummyTvShow.id)
        Assert.assertEquals(dummyTvShow.id, viewModel.getTvShowById(dummyTvShow.id).value?.id)
        Assert.assertEquals(dummyTvShow.title, viewModel.getTvShowById(dummyTvShow.id).value?.title)
        Assert.assertEquals(
            dummyTvShow.description,
            viewModel.getTvShowById(dummyTvShow.id).value?.description
        )
        Assert.assertEquals(
            dummyTvShow.image,
            viewModel.getTvShowById(dummyTvShow.id).value?.image
        )
        Assert.assertEquals(
            dummyTvShow.releaseDate,
            viewModel.getTvShowById(dummyTvShow.id).value?.releaseDate
        )
    }
}