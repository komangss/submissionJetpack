package com.komangss.submissionjetpack.favorites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.komangss.submissionjetpack.home.HomeActivity
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

    override fun onBackPressed() {
        startActivity(Intent(this@FavoriteActivity, HomeActivity::class.java))
        finish()
        super.onBackPressed()
    }
}