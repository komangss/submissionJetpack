package com.komangss.submissionjetpack.ui.movie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.komangss.submissionjetpack.R

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}