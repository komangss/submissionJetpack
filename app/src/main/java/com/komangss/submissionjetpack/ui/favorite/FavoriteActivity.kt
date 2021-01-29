package com.komangss.submissionjetpack.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.komangss.submissionjetpack.R
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager_activity_favorite.adapter = sectionsPagerAdapter
        tabs_activity_favorite.setupWithViewPager(view_pager_activity_favorite)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Favorite List"
    }
}