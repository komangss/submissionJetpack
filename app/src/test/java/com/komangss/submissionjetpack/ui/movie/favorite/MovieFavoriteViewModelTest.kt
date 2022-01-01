package com.komangss.submissionjetpack.ui.movie.favorite

import androidx.lifecycle.liveData
import androidx.paging.PagedList
import com.komangss.submissionjetpack.core.data.CatalogRepository
import com.komangss.submissionjetpack.core.domain.model.Movie
import com.komangss.submissionjetpack.utils.PagedListUtil.mockPagedList
import com.komangss.submissionjetpack.utils.datagenerator.MovieDataGenerator.movieDomainList
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test


class  MovieFavoriteViewModelTest {
    @Test
    fun getFavoriteMovies() {
        val movieResults: List<Movie> = movieDomainList

        val mockedMoviePagedList: PagedList<Movie> = mockPagedList(movieResults)
        val repoResult = liveData {
            emit(mockedMoviePagedList)
        }

        val repository = com.nhaarman.mockitokotlin2.mock<CatalogRepository> {
            onBlocking { getFavoriteMovies() } doReturn repoResult
        }

        repository.getFavoriteMovies()

        verify(repository).getFavoriteMovies()
        assertNotNull(mockedMoviePagedList)
        assertEquals(movieResults.size, mockedMoviePagedList.size)
    }
}