package com.komangss.submissionjetpack.ui.movie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.viewmodel.ViewModelFactory

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]

        viewModel.detailMovie(movieId).observe(this, Observer {
            Toast.makeText(this, it.title, Toast.LENGTH_LONG).show()
        })
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}