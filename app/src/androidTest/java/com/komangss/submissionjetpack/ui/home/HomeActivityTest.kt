package com.komangss.submissionjetpack.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.utils.DataGenerator
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    private val dummyMovies = DataGenerator.generateDummyMovies()
    private val dummyTvShows = DataGenerator.generateDummyTvShows()


    @get:Rule
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun loadMovies() {
        onView(withId(R.id.fragment_movie_rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_movie_rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.fragment_tvshow_rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_tvshow_rv_tvshow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShows.size))
    }
}