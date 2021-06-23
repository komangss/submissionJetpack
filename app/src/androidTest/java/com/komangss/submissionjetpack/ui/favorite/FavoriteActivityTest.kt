package com.komangss.submissionjetpack.ui.favorite

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.rule.lazyActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FavoriteActivityTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun preparation() {
        hiltAndroidRule.inject()
    }

    @Test
    fun swipePage() {
        lazyActivityScenarioRule<FavoriteActivity>(launchActivity = true).launch()

        Espresso.onView(ViewMatchers.withId(R.id.view_pager_activity_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.view_pager_activity_favorite))
            .perform(ViewActions.swipeLeft())
        Espresso.onView(ViewMatchers.withId(R.id.view_pager_activity_favorite))
            .perform(ViewActions.swipeRight())
    }
}