package com.komangss.submissionjetpack.ui.movie.detail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.movie.detail.MovieDetailActivity.Companion.EXTRA_MOVIE_ID
import com.komangss.submissionjetpack.ui.rule.lazyActivityScenarioRule
import com.komangss.submissionjetpack.utils.EspressoIdlingResources
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MovieDetailActivityTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun preparation() {
        hiltAndroidRule.inject()
    }

    private var dummyMovieId: Int = 123

    @Test
    fun testMovieTitleShowUp() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(), MovieDetailActivity::class.java
        ).putExtra(EXTRA_MOVIE_ID, dummyMovieId)

        lazyActivityScenarioRule<MovieDetailActivity>(launchActivity = false).launch(intent)
        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_movie_detail_movie_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_movie_detail_movie_description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_movie_detail_movie_rating))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
    }
}