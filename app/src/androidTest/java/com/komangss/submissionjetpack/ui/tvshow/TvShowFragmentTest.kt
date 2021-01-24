package com.komangss.submissionjetpack.ui.tvshow

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.komangss.submissionjetpack.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TvShowFragmentTest {
    @Test
    fun testRecyclerViewIsDisplayed() {
        launchFragmentInContainer<TvShowFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.fragment_tvshow_rv_tvshow))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}