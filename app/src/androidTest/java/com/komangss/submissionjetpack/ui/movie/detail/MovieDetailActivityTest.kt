package com.komangss.submissionjetpack.ui.movie.detail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.GsonBuilder
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.mapper.CatalogMovieMapper
import com.komangss.submissionjetpack.framework.network.model.MovieResultResponse
import com.komangss.submissionjetpack.ui.Utils.getJsonFromAssets
import com.komangss.submissionjetpack.ui.movie.detail.MovieDetailActivity.Companion.EXTRA_MOVIE_ID
import com.komangss.submissionjetpack.ui.rule.lazyActivityScenarioRule
import com.komangss.submissionjetpack.utils.EspressoIdlingResources
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailActivityTest {

    private lateinit var dummyMovie: Movie

    @get:Rule
    val rule = lazyActivityScenarioRule<MovieDetailActivity>(launchActivity = false)

    @Before
    fun setUp() {
        val gson = GsonBuilder().create()
        val jsonResult =
            getJsonFromAssets(
                InstrumentationRegistry.getInstrumentation().context,
                "MovieResponse.json"
            )

        val movieResultResponse: MovieResultResponse = gson.fromJson(
            jsonResult,
            MovieResultResponse::class.java
        )

        val mapper = CatalogMovieMapper()
        val entities = mapper.responsesToEntities(movieResultResponse.results)

        dummyMovie = mapper.entitiesToDomains(entities)[0]

        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
    }

    @Test
    fun testMovieTitleShowUp() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(), MovieDetailActivity::class.java
        ).putExtra(EXTRA_MOVIE_ID, dummyMovie.id)

        rule.launch(intent)

        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_movie_detail_movie_title))
            .check(ViewAssertions.matches(withText(dummyMovie.title)))

    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
    }
}