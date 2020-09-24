package com.komangss.submissionjetpack.ui.movie.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.data.CatalogRepository
import com.komangss.submissionjetpack.data.source.local.entity.MovieEntity
import com.komangss.submissionjetpack.utils.EntityDataGenerator
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
    private lateinit var dummyMovie : MovieEntity
    private val catalogRepository = mock(CatalogRepository::class.java)

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(catalogRepository)
        dummyMovie = MovieEntity(
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
        val mutableMovie = MutableLiveData<MovieEntity>()
        mutableMovie.value = EntityDataGenerator.getMovieById(dummyMovie.id)
        `when`(catalogRepository.getMovieById(dummyMovie.id)).thenReturn(mutableMovie)
        val observer: Observer<MovieEntity> = mock(Observer::class.java) as Observer<MovieEntity>
        viewModel.detailMovie(dummyMovie.id).observeForever(observer)
        verify(catalogRepository).getMovieById(dummyMovie.id)
        assertEquals(dummyMovie.id, viewModel.detailMovie(dummyMovie.id).value?.id)
        assertEquals(dummyMovie.title, viewModel.detailMovie(dummyMovie.id).value?.title)
        assertEquals(
            dummyMovie.description,
            viewModel.detailMovie(dummyMovie.id).value?.description
        )
        assertEquals(dummyMovie.image, viewModel.detailMovie(dummyMovie.id).value?.image)
        assertEquals(
            dummyMovie.releaseDate,
            viewModel.detailMovie(dummyMovie.id).value?.releaseDate
        )
    }
}