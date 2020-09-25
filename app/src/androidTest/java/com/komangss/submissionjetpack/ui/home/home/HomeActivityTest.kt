package com.komangss.submissionjetpack.ui.home.home

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.home.HomeActivity
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    @get:Rule
    val activityTestRule : ActivityTestRule<HomeActivity> =
        ActivityTestRule(HomeActivity::class.java)

    private lateinit var activity : HomeActivity

    @Before
    fun setUp() {
        activity = activityTestRule.activity
        activityTestRule.launchActivity(Intent())
        assertThat(activityTestRule, notNullValue())
    }

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