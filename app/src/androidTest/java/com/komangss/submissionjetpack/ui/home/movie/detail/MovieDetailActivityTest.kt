package com.komangss.submissionjetpack.ui.home.movie.detail

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.movie.detail.MovieDetailActivity
import com.komangss.submissionjetpack.ui.movie.detail.MovieDetailActivity.Companion.EXTRA_MOVIE_ID
import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
import com.komangss.submissionjetpack.utils.EspressoIdlingResources
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailActivityTest {

    private val dummyMovie = DomainModelDataGenerator.generateDummyMovies()[0]

        @get:Rule
        val activityTestRule : ActivityTestRule<MovieDetailActivity> =
            ActivityTestRule(
                MovieDetailActivity::class.java,
                true,
                false
            )

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
        val intent = Intent()
        intent.putExtra(EXTRA_MOVIE_ID, dummyMovie.id)
        activityTestRule.launchActivity(intent)
    }

    @Test
    fun testMovieTitleShowUp() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_movie_detail_movie_title))
            .check(ViewAssertions.matches(withText(dummyMovie.title)))
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
    }
}