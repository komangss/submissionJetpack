package com.komangss.submissionjetpack.favorites

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.komangss.submissionjetpack.favorites.databinding.ActivityFavoriteBinding
import com.komangss.submissionjetpack.favorites.movie.MovieFavoriteFragment
import com.komangss.submissionjetpack.favorites.tvshow.TvShowFavoriteFragment
import com.komangss.submissionjetpack.home.HomeActivity

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPagerActivityFavorite.adapter = sectionsPagerAdapter
        TabLayoutMediator(
            binding.tabsActivityFavorite,
            binding.viewPagerActivityFavorite
        ) { tab, position ->
            run {
                if (position == 0)
                    tab.text = "Favorite Movie"
                else
                    tab.text = "Favorite Tv Show"
            }
        }.attach()
        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Favorite List"
    }

    override fun onBackPressed() {
        startActivity(Intent(this@FavoriteActivity, HomeActivity::class.java))
        finish()
        super.onBackPressed()
    }


    internal class SectionsPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        companion object {
            @StringRes
            private val TAB_TITLES = intArrayOf(R.string.favorite_movie, R.string.favorite_tvshow)
        }

        override fun getItemCount(): Int = TAB_TITLES.size

        override fun createFragment(position: Int): Fragment =
            when (position) {
                0 -> MovieFavoriteFragment()
                1 -> TvShowFavoriteFragment()
                else -> throw IllegalStateException("Invalid adapter position")
            }
    }
}