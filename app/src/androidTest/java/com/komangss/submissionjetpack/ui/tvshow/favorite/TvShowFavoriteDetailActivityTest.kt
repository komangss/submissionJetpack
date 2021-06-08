package com.komangss.submissionjetpack.ui.tvshow.favorite

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.GsonBuilder
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.mapper.CatalogTvShowMapper
import com.komangss.submissionjetpack.framework.network.model.TvShowResultResponse
import com.komangss.submissionjetpack.ui.Utils
import com.komangss.submissionjetpack.ui.rule.lazyActivityScenarioRule
import com.komangss.submissionjetpack.ui.tvshow.favorite.TvShowFavoriteDetailActivity.Companion.EXTRA_TV_SHOW_ID
import com.komangss.submissionjetpack.utils.EspressoIdlingResources
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TvShowFavoriteDetailActivityTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun preparation() {
        hiltAndroidRule.inject()
    }

    private lateinit var dummyTvShow: TvShow

    @Before
    fun setUp() {
        val gson = GsonBuilder().create()
        val jsonResult =
            Utils.getJsonFromAssets(
                InstrumentationRegistry.getInstrumentation().context,
                "TvShowResponse.json"
            )

        val tvShowResultResponse: TvShowResultResponse = gson.fromJson(
            jsonResult,
            TvShowResultResponse::class.java
        )

        val mapper = CatalogTvShowMapper()
        val entities = mapper.responsesToEntities(tvShowResultResponse.results ?: listOf())

        dummyTvShow = mapper.entitiesToDomains(entities)[0]

    }

    @Test
    fun testTvShowTitleShowUp() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(), TvShowFavoriteDetailActivity::class.java
        ).putExtra(EXTRA_TV_SHOW_ID, dummyTvShow.id)

        lazyActivityScenarioRule<TvShowFavoriteDetailActivity>(launchActivity = false).launch(intent)
        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)

        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_tv_show_detail_tv_show_title))
            .check(ViewAssertions.matches(ViewMatchers.withText(dummyTvShow.name)))
        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_tv_show_detail_tv_show_description))
            .check(ViewAssertions.matches(ViewMatchers.withText(dummyTvShow.description)))

        val voteAverageText = "${dummyTvShow.voteAverage.div(2).toFloat()} / 5"
        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_tv_show_detail_tv_show_rating))
            .check(ViewAssertions.matches(ViewMatchers.withText(voteAverageText)))


    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
    }
}