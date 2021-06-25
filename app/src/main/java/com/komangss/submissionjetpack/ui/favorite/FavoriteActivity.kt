package com.komangss.submissionjetpack.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.komangss.submissionjetpack.databinding.ActivityFavoriteBinding
import com.komangss.submissionjetpack.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.viewPagerActivityFavorite.adapter = sectionsPagerAdapter
        binding.tabsActivityFavorite.setupWithViewPager(binding.viewPagerActivityFavorite)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Favorite List"
    }

    override fun onBackPressed() {
        startActivity(Intent(this@FavoriteActivity, HomeActivity::class.java))
        finish()
        super.onBackPressed()
    }
}