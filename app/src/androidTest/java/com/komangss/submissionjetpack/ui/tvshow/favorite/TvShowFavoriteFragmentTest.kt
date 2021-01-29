package com.komangss.submissionjetpack.ui.tvshow.favorite

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.komangss.submissionjetpack.R
import org.junit.Test

class TvShowFavoriteFragmentTest {

    @Test
    fun testRecyclerViewIsDisplayed() {
        launchFragmentInContainer<TvShowFavoriteFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.fragment_tvshow_favorite_rv_tvshow))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}