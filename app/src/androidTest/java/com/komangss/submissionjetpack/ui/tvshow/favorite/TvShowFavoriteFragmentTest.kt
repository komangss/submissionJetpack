package com.komangss.submissionjetpack.ui.tvshow.favorite

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.HiltExt.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TvShowFavoriteFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun preparation() {
        hiltAndroidRule.inject()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testRecyclerViewIsDisplayed() {
        launchFragmentInHiltContainer<TvShowFavoriteFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.fragment_tvshow_favorite_rv_tvshow))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}