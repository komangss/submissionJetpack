package com.komangss.submissionjetpack.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.komangss.submissionjetpack.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager_activity_home.adapter = sectionsPagerAdapter
        tabs_activity_home.setupWithViewPager(view_pager_activity_home)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Movie Catalog"
    }

}