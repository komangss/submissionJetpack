package com.komangss.submissionjetpack.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogRepository: CatalogRepository

    @Mock
    private lateinit var observer: Observer<List<Movie>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(catalogRepository)
    }

    @Test
    fun getMovies() {
//        val dummyMovies = DomainModelDataGenerator.generateDummyMovies()
//        val movies = MutableLiveData<List<Movie>>()
//        movies.value = dummyMovies
//
//        `when`(catalogRepository.getAllMovies()).thenReturn(movies)
//        val movieList = viewModel.getMovies().value
//        verify(catalogRepository).getAllMovies()
//        assertNotNull(movieList)
//        assertEquals(16, movieList?.size)
//
//        viewModel.getMovies().observeForever(observer)
//        verify(observer).onChanged(dummyMovies)
    }
}