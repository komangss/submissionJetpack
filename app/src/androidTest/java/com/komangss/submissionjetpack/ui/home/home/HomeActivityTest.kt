package com.komangss.submissionjetpack.ui.home.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.home.HomeActivity
import com.komangss.submissionjetpack.ui.home.rule.lazyActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    @get:Rule
    val rule = lazyActivityScenarioRule<HomeActivity>(launchActivity = true)

    @Test
    fun swipePage() {
        onView(withId(R.id.view_pager))
            .check(matches(isDisplayed()))
        onView(withId(R.id.view_pager))
            .perform(swipeLeft())
        onView(withId(R.id.view_pager))
            .perform(swipeRight())
    }
}