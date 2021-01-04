package com.komangss.submissionjetpack.ui.tvshow.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.viewmodel.ViewModelFactory

class TvShowDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        supportActionBar?.title = "Tv Show"

        val tvShowId = intent.getIntExtra(EXTRA_TV_SHOW_ID, -1)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[TvShowDetailViewModel::class.java]

        viewModel.getTvShowById(tvShowId).observe(this, {
//            TODO : Add Layout

            supportActionBar?.title = it.title
        })
    }

    companion object {
        const val EXTRA_TV_SHOW_ID = "extra_tv_show_id"
    }
}