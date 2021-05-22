package com.komangss.submissionjetpack.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.favorite.FavoriteActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home_favorite -> {
                startActivity(Intent(this@HomeActivity, FavoriteActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}