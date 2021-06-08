package com.komangss.submissionjetpack.ui.movie

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.HiltExt.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MovieFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun preparation() {
        hiltAndroidRule.inject()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testRecyclerViewIsDisplayed() {
        launchFragmentInHiltContainer<MovieFragment>()
        onView(withId(R.id.fragment_movie_rv_movie)).check(matches(isDisplayed()))
    }
}