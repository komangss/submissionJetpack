package com.komangss.submissionjetpack.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.rule.lazyActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeActivityTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun preparation() {
        hiltAndroidRule.inject()
    }

    @Test
    fun swipePage() {
        lazyActivityScenarioRule<HomeActivity>(launchActivity = true).launch()
        onView(withId(R.id.view_pager_activity_home))
            .check(matches(isDisplayed()))
        onView(withId(R.id.view_pager_activity_home))
            .perform(swipeLeft())
        onView(withId(R.id.view_pager_activity_home))
            .perform(swipeRight())
    }
}