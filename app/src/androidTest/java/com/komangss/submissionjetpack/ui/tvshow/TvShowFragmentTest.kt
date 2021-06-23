package com.komangss.submissionjetpack.ui.tvshow

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.HiltExt
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TvShowFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun preparation() {
        hiltAndroidRule.inject()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testRecyclerViewIsDisplayed() {
        HiltExt.launchFragmentInHiltContainer<TvShowFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.fragment_tvshow_rv_tvshow))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}