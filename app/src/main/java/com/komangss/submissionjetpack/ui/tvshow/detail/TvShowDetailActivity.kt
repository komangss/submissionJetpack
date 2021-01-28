package com.komangss.submissionjetpack.ui.tvshow.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.viewmodel.ViewModelFactory
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.android.synthetic.main.activity_tv_show_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class TvShowDetailActivity : AppCompatActivity() {
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        supportActionBar?.title = getString(R.string.tvshow)

        val tvShowId = intent.getIntExtra(EXTRA_TV_SHOW_ID, -1)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[TvShowDetailViewModel::class.java]

        viewModel.detailTvShow(tvShowId).observe(this, {
            when (it) {
                is Resource.Success -> {
                    tv_activity_tv_show_detail_tv_show_title.text = it.data.name
                    tv_activity_tv_show_detail_tv_show_description.text = it.data.description
                    val voteAverage = it.data.voteAverage.div(2).toFloat()
                    item_tv_show_tvshow_rating_bar.rating = voteAverage
                    tv_activity_tv_show_detail_tv_show_rating.text = "$voteAverage / 5"
                    supportActionBar?.title = it.data.name

                    Glide.with(this@TvShowDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${it.data.posterUrlPath}")
                        .into(image_view_activity_tv_show_detail_tv_show_poster)

                    Glide.with(this@TvShowDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${it.data.backdropUrlPath}")
                        .fitCenter()
                        .centerCrop()
                        .into(image_view_activity_tv_show_detail_tv_show_backdrop)

                }
                is Resource.Error -> {
                    Toast.makeText(
                        this@TvShowDetailActivity,
                        it.error?.errorDescription,
                        Toast.LENGTH_LONG
                    ).show()
                }
                Resource.InProgress -> {
                    Toast.makeText(
                        this@TvShowDetailActivity,
                        "Load From Network...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    companion object {
        const val EXTRA_TV_SHOW_ID = "extra_tv_show_id"
    }
}