package com.komangss.submissionjetpack.ui.tvshow.favorite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.favorite.FavoriteActivity
import com.komangss.submissionjetpack.ui.tvshow.detail.TvShowDetailViewModel
import com.komangss.submissionjetpack.viewmodel.ViewModelFactory
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.android.synthetic.main.activity_tv_show_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TvShowFavoriteDetailActivity : AppCompatActivity() {

    private var isFav = false

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        supportActionBar?.title = getString(R.string.tvshow)

        val tvShowId = intent.getIntExtra(EXTRA_TV_SHOW_ID, -1)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[TvShowDetailViewModel::class.java]

        viewModel.setTvShowId(tvShowId)

        viewModel.tvShow.observe(this, {
            when (it) {
                is Resource.Success -> {
                    val tvShow = it.data
                    tv_activity_tv_show_detail_tv_show_title.text = tvShow.name
                    tv_activity_tv_show_detail_tv_show_description.text = tvShow.description
                    val voteAverage = tvShow.voteAverage.div(2).toFloat()
                    item_tv_show_tvshow_rating_bar.rating = voteAverage
                    tv_activity_tv_show_detail_tv_show_rating.text = "$voteAverage / 5"
                    supportActionBar?.title = tvShow.name

                    Glide.with(this@TvShowFavoriteDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${tvShow.posterUrlPath}")
                        .into(image_view_activity_tv_show_detail_tv_show_poster)

                    Glide.with(this@TvShowFavoriteDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${tvShow.backdropUrlPath}")
                        .fitCenter()
                        .centerCrop()
                        .into(image_view_activity_tv_show_detail_tv_show_backdrop)

                    isFav = tvShow.isFavorite

                    setFavorite()

                    fab_activity_tv_show_detail_favorite.setOnClickListener {
                        lifecycleScope.launch {
                            viewModel.setFavorite(tvShow)
                            withContext(Dispatchers.Main) {
                                finish()
                                startActivity(Intent(this@TvShowFavoriteDetailActivity, FavoriteActivity::class.java))
                            }
                        }
                    }

                }
                is Resource.Error -> {
                    Toast.makeText(
                        this@TvShowFavoriteDetailActivity,
                        it.error?.errorDescription,
                        Toast.LENGTH_LONG
                    ).show()
                }
                Resource.InProgress -> {
                    Toast.makeText(
                        this@TvShowFavoriteDetailActivity,
                        "Loading...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

    }

    private fun setFavorite() {
        if (isFav) {
            fab_activity_tv_show_detail_favorite.setImageResource(R.drawable.ic_favorite)
        } else {
            fab_activity_tv_show_detail_favorite.setImageResource(R.drawable.ic_broken_heart)
        }

    }

    companion object {
        const val EXTRA_TV_SHOW_ID = "extra_tv_show_id"
    }

    override fun onBackPressed() {
        startActivity(Intent(this@TvShowFavoriteDetailActivity, FavoriteActivity::class.java))
        finish()
        super.onBackPressed()
    }
}