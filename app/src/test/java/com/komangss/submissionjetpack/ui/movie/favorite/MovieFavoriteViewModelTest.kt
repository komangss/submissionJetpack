package com.komangss.submissionjetpack.ui.movie.favorite

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.utils.PagedListUtil.mockPagedList
import com.komangss.submissionjetpack.utils.datagenerator.DomainModelDataGenerator
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.mockito.Mockito.mock


class  MovieFavoriteViewModelTest {
    @Test
    fun getFavoriteMovies() {
        val movieResults: List<Movie> = DomainModelDataGenerator.generateDummyMovies()
        val dataSourceFactory: DataSource.Factory<Int, MovieEntity> =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>

        val repository = com.nhaarman.mockitokotlin2.mock<CatalogRepository> {
            onBlocking { getFavoriteMovies() } doReturn dataSourceFactory
        }

        val result: PagedList<Movie> = mockPagedList(movieResults)

        repository.getFavoriteMovies()

        verify(repository).getFavoriteMovies()
        assertNotNull(result)
        assertEquals(movieResults.size, result.size)
    }
}