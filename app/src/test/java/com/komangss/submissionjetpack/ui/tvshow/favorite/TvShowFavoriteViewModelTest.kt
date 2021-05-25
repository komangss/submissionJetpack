package com.komangss.submissionjetpack.ui.tvshow.favorite

import androidx.lifecycle.liveData
import androidx.paging.PagedList
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.utils.PagedListUtil.mockPagedList
import com.komangss.submissionjetpack.utils.datagenerator.DomainModelDataGenerator
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class TvShowFavoriteViewModelTest {
    @Test
    fun getFavoriteTvShows() {
        val tvShowResults: List<TvShow> = DomainModelDataGenerator.generateDummyTvShows()

        val mockedTvShowPagedList: PagedList<TvShow> = mockPagedList(tvShowResults)
        val repoResult = liveData {
            emit(mockedTvShowPagedList)
        }

        val repository = com.nhaarman.mockitokotlin2.mock<CatalogRepository> {
            onBlocking { getFavoriteTvShows() } doReturn repoResult
        }

        repository.getFavoriteTvShows()

        verify(repository).getFavoriteTvShows()
        assertNotNull(mockedTvShowPagedList)
        assertEquals(tvShowResults.size, mockedTvShowPagedList.size)
    }
}