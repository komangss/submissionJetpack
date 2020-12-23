//package com.komangss.submissionjetpack.ui.movie
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Observer
//import com.komangss.submissionjetpack.business.domain.model.Movie
//import com.komangss.submissionjetpack.business.repository.CatalogRepository
//import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
//import com.komangss.submissionjetpack.vo.Resource
//import com.nhaarman.mockitokotlin2.verify
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.Mockito.`when`
//import org.mockito.Mockito.mock
//import org.mockito.junit.MockitoJUnitRunner
//
//
//@Suppress("UNCHECKED_CAST")
//@RunWith(MockitoJUnitRunner::class)
//class MovieViewModelTest {
//
//    private lateinit var viewModel: MovieViewModel
//
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var catalogRepository: CatalogRepository
//
//    @Mock
//    private lateinit var observer: Observer<List<Movie>>
//
//    @Before
//    fun setUp() {
//        viewModel = MovieViewModel(catalogRepository)
//    }
//
//    @Test
//    fun getMovies() {
//        val dummyListMovies = DomainModelDataGenerator.generateDummyMovies()
//        val movies = MutableLiveData<Resource<List<Movie>>>()
//        movies.value = Resource.Success(dummyListMovies)
//
//        `when`(catalogRepository.getAllMovies()).thenReturn(movies)
//        val movieList = viewModel.getMovies().value
//        val observer : Observer<Resource<List<Movie>>> = mock(Observer::class.java) as Observer<Resource<List<Movie>>>
//        verify(catalogRepository).getAllMovies()
//
//        viewModel.getMovies().observeForever(observer)
//        verify(observer).onChanged(movieList)
//    }
//}