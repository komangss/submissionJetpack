package com.komangss.submissionjetpack.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.komangss.submissionjetpack.data.CatalogRepository
import com.komangss.submissionjetpack.data.source.local.entity.MovieEntity
import com.komangss.submissionjetpack.utils.EntityDataGenerator
import com.komangss.submissionjetpack.utils.ResponseDataGenerator
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
    private lateinit var observer: Observer<List<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(catalogRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = EntityDataGenerator.generateDummyMovies()
        val movies = MutableLiveData<List<MovieEntity>>()
        movies.value = dummyMovies

        `when`(catalogRepository.getAllMovies()).thenReturn(movies)
        val movieEntities = viewModel.getMovies().value
        verify(catalogRepository).getAllMovies()
        assertNotNull(movieEntities)
        assertEquals(16, movieEntities?.size)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}