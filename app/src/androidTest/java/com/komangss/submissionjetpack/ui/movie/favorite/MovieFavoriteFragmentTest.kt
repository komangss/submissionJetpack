package com.komangss.submissionjetpack.ui.movie.favorite

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
class MovieFavoriteFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun preparation() {
        hiltAndroidRule.inject()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testRecyclerViewIsDisplayed() {
        HiltExt.launchFragmentInHiltContainer<MovieFavoriteFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.fragment_movie_favorite_rv_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}