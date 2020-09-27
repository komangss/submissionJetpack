package com.komangss.submissionjetpack.ui.home.movie.detail

import android.content.Intent
import androidx.test.rule.ActivityTestRule
import com.komangss.submissionjetpack.ui.movie.detail.MovieDetailActivity
import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
import org.junit.Before
import org.junit.Rule

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
        val intent = Intent()
        intent.putExtra("EXTRA_MOVIE_ID", dummyMovie.id)
        activityTestRule.launchActivity(intent)
    }
}