package com.komangss.submissionjetpack.ui.tvshow.detail

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
import com.komangss.submissionjetpack.ui.tvshow.detail.TvShowDetailActivity.Companion.EXTRA_TV_SHOW_ID
import com.komangss.submissionjetpack.utils.EspressoIdlingResources
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TvShowDetailActivityTest {
    private lateinit var dummyTvShow : TvShow

    @get:Rule
    val rule = lazyActivityScenarioRule<TvShowDetailActivity>(launchActivity = false)

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

        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
    }

    @Test
    fun testTvShowTitleShowUp() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(), TvShowDetailActivity::class.java
        ).putExtra(EXTRA_TV_SHOW_ID, dummyTvShow.id)

        rule.launch(intent)

        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_tv_show_detail_tv_show_title))
            .check(ViewAssertions.matches(ViewMatchers.withText(dummyTvShow.name)))
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
    }
}