package com.komangss.submissionjetpack.ui.movie.favorite

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.CustomMatchers
import com.komangss.submissionjetpack.ui.movie.detail.MovieDetailActivity.Companion.EXTRA_MOVIE_ID
import com.komangss.submissionjetpack.ui.rule.lazyActivityScenarioRule
import com.komangss.submissionjetpack.utils.EspressoIdlingResources
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MovieFavoriteDetailActivityTest {
    private var dummyMovieId : Int = 123

    @get:Rule
    val rule = lazyActivityScenarioRule<MovieFavoriteDetailActivity>(launchActivity = false)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
    }

    @Test
    fun testMovieTitleShowUp() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(), MovieFavoriteDetailActivity::class.java
        ).putExtra(EXTRA_MOVIE_ID, dummyMovieId)

        rule.launch(intent)

        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_movie_detail_movie_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_movie_detail_movie_description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_movie_detail_movie_rating))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testFavoriteThisMovie() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(), MovieFavoriteDetailActivity::class.java
        ).putExtra(EXTRA_MOVIE_ID, dummyMovieId)

        rule.launch(intent)

        Espresso.onView(ViewMatchers.withId(R.id.fab__activity_movie_detail_favorite))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.fab__activity_movie_detail_favorite))
            .check(ViewAssertions.matches(CustomMatchers.withImageDrawable(R.drawable.ic_favorite)))
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
    }
}