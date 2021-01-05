package com.komangss.submissionjetpack.ui.tvshow.detail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.rule.lazyActivityScenarioRule
import com.komangss.submissionjetpack.ui.tvshow.detail.TvShowDetailActivity.Companion.EXTRA_TV_SHOW_ID
import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
import com.komangss.submissionjetpack.utils.EspressoIdlingResources
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TvShowDetailActivityTest {
    private val dummyTvShow = DomainModelDataGenerator.generateDummyTvShows()[0]

    @get:Rule
    val rule = lazyActivityScenarioRule<TvShowDetailActivity>(launchActivity = false)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
    }

    @Test
    fun testTvShowTitleShowUp() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(), TvShowDetailActivity::class.java
        ).putExtra(EXTRA_TV_SHOW_ID, dummyTvShow.id)

        rule.launch(intent)

        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_tv_show_detail_tv_show_title))
            .check(ViewAssertions.matches(ViewMatchers.withText(dummyTvShow.title)))
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
    }
}