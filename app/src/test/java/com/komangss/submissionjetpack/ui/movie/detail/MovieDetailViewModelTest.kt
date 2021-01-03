package com.komangss.submissionjetpack.ui.movie.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.repository.CatalogRepository
import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class MovieDetailViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel : MovieDetailViewModel
    private lateinit var dummyMovie : Movie
    private val catalogRepository = mock(CatalogRepository::class.java)

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(catalogRepository)
        dummyMovie = Movie(
            1,
            "Alita : Battle Angel",
            "Robert Rodriguez",
            "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
            R.drawable.poster_alita,
            "2019",
            "67"
        )
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun detailMovie() {
        val mutableMovie = MutableLiveData<Movie>()
        mutableMovie.value = DomainModelDataGenerator.getMovieById(dummyMovie.id)
        `when`(catalogRepository.getMovieById(dummyMovie.id)).thenReturn(mutableMovie)
        val observer: Observer<Movie> = mock(Observer::class.java) as Observer<Movie>
        viewModel.detailMovie(dummyMovie.id).observeForever(observer)
        verify(catalogRepository).getMovieById(dummyMovie.id)
        assertEquals(dummyMovie.id, viewModel.detailMovie(dummyMovie.id).value?.id)
        assertEquals(dummyMovie.title, viewModel.detailMovie(dummyMovie.id).value?.title)
        assertEquals(
            dummyMovie.description,
            viewModel.detailMovie(dummyMovie.id).value?.description
        )
        assertEquals(dummyMovie.imageUrl, viewModel.detailMovie(dummyMovie.id).value?.imageUrl)
        assertEquals(
            dummyMovie.releaseDate,
            viewModel.detailMovie(dummyMovie.id).value?.releaseDate
        )
    }
}