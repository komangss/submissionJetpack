package com.komangss.submissionjetpack.ui.tvshow.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.BuildConfig
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_tv_show_detail.*

class TvShowDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        supportActionBar?.title = getString(R.string.tvshow)

        val tvShowId = intent.getIntExtra(EXTRA_TV_SHOW_ID, -1)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[TvShowDetailViewModel::class.java]

//        viewModel.getTvShowById(tvShowId).observe(this, {
//            tv_activity_tv_show_detail_tv_show_title.text = it.title
//            tv_activity_tv_show_detail_tv_show_description.text = it.description
//            tv_activity_tv_show_detail_tv_show_rating_tv_show.text = it.rating
//            Glide.with(this@TvShowDetailActivity)
//                .load(resources.getIdentifier(it.image, "drawable", BuildConfig.APPLICATION_ID))
//                .into(image_view_activity_tv_show_detail_tv_show_poster)
//
//            supportActionBar?.title = it.title
//        })
    }

    companion object {
        const val EXTRA_TV_SHOW_ID = "extra_tv_show_id"
    }
}