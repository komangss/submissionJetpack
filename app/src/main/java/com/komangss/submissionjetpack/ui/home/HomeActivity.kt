package com.komangss.submissionjetpack.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.databinding.ActivityHomeBinding
import com.komangss.submissionjetpack.ui.favorite.FavoriteActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding: ActivityHomeBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        binding.viewPagerActivityHome.adapter = sectionsPagerAdapter
        binding.tabsActivityHome.setupWithViewPager(binding.viewPagerActivityHome)


        supportActionBar?.elevation = 0f
        // ToDo : Change Title According to current fragment
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