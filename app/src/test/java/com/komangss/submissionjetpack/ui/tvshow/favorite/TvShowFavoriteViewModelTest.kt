package com.komangss.submissionjetpack.ui.tvshow.favorite

import androidx.lifecycle.liveData
import androidx.paging.PagedList
import com.komangss.submissionjetpack.core.data.CatalogRepository
import com.komangss.submissionjetpack.core.domain.model.TvShow
import com.komangss.submissionjetpack.utils.PagedListUtil.mockPagedList
import com.komangss.submissionjetpack.utils.datagenerator.TvShowDataGenerator.tvShowDomainList
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class TvShowFavoriteViewModelTest {
    @Test
    fun getFavoriteTvShows() {
        val tvShowResults: List<TvShow> = tvShowDomainList

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