package com.komangss.submissionjetpack.ui.tvshow

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.movie.MovieFragment
import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TvShowFragmentTest {

    private val dummyTvShowSize = DomainModelDataGenerator.generateDummyTvShows().size

    @Test
    fun testRecyclerViewIsDisplayed() {
        launchFragmentInContainer<MovieFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.fragment_tvshow_rv_tvshow))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testTotalItemMatches() {
        launchFragmentInContainer<MovieFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.fragment_tvshow_rv_tvshow)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShowSize)
        )
    }
}