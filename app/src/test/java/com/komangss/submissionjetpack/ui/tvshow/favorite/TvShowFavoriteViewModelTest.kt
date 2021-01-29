package com.komangss.submissionjetpack.ui.tvshow.favorite

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.utils.PagedListUtil.mockPagedList
import com.komangss.submissionjetpack.utils.datagenerator.DomainModelDataGenerator
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.mockito.Mockito.mock


class TvShowFavoriteViewModelTest {
    @Test
    fun getFavoriteTvShows() {
        val tvShowResults: List<TvShow> = DomainModelDataGenerator.generateDummyTvShows()
        val dataSourceFactory: DataSource.Factory<Int, TvShowEntity> =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>

        val repository = com.nhaarman.mockitokotlin2.mock<CatalogRepository> {
            onBlocking { getFavoriteTvShows() } doReturn dataSourceFactory
        }

        val result: PagedList<TvShow> = mockPagedList(tvShowResults)

        repository.getFavoriteTvShows()

        verify(repository).getFavoriteTvShows()
        assertNotNull(result)
        assertEquals(tvShowResults.size, result.size)
    }
}