//package com.komangss.submissionjetpack.ui.movie.detail
//
//import android.content.Intent
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.espresso.Espresso
//import androidx.test.espresso.IdlingRegistry
//import androidx.test.espresso.assertion.ViewAssertions
//import androidx.test.espresso.matcher.ViewMatchers
//import androidx.test.espresso.matcher.ViewMatchers.withText
//import com.komangss.submissionjetpack.R
//import com.komangss.submissionjetpack.ui.rule.lazyActivityScenarioRule
//import com.komangss.submissionjetpack.ui.movie.detail.MovieDetailActivity.Companion.EXTRA_MOVIE_ID
//import com.komangss.submissionjetpack.utils.DomainModelDataGenerator
//import com.komangss.submissionjetpack.utils.EspressoIdlingResources
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//class MovieDetailActivityTest {
//
//    private val dummyMovie = DomainModelDataGenerator.generateDummyMovies()[0]
//
//    @get:Rule
//    val rule = lazyActivityScenarioRule<MovieDetailActivity>(launchActivity = false)
//
//    @Before
//    fun setUp() {
//        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
//    }
//
//    @Test
//    fun testMovieTitleShowUp() {
//        val intent = Intent(
//            ApplicationProvider.getApplicationContext(), MovieDetailActivity::class.java
//        ).putExtra(EXTRA_MOVIE_ID, dummyMovie.id)
//
//        rule.launch(intent)
//
//        Espresso.onView(ViewMatchers.withId(R.id.tv_activity_movie_detail_movie_title))
//            .check(ViewAssertions.matches(withText(dummyMovie.title)))
//
//    }
//
//    @After
//    fun unregisterIdlingResource() {
//        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
//    }
//}