package com.komangss.submissionjetpack.ui.movie

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieFragmentTest {

    private val dummyMovieSize = DomainModelDataGenerator.generateDummyMovies().size

    @Test
    fun testRecyclerViewIsDisplayed() {
        launchFragmentInContainer<MovieFragment>()
        onView(withId(R.id.fragment_movie_rv_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun testTotalItemMatches() {
        launchFragmentInContainer<MovieFragment>()
        onView(withId(R.id.fragment_movie_rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovieSize)
        )
    }
}