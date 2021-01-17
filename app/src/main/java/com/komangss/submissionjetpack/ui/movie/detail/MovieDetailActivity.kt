package com.komangss.submissionjetpack.ui.movie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.BuildConfig
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.viewmodel.ViewModelFactory
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.items_movie_and_tvshow.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MovieDetailActivity : AppCompatActivity() {
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.title = getString(R.string.movie)

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]

        viewModel.detailMovie(movieId).observe(this, {
            when (it) {
                is Resource.Success -> {
                    tv_activity_movie_detail_movie_title.text = it.data.title
                    tv_activity_movie_detail_movie_description.text = it.data.description
                    val voteAverage = it.data.voteAverage?.div(2)?.toFloat()
                    if (voteAverage != null) {
                        item_movie_tvshow_rating_bar.rating = voteAverage
                    }

                    Glide.with(this@MovieDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${it.data.posterPath}")
                        .into(image_view_activity_movie_detail_movie_poster)

                    Glide.with(this@MovieDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${it.data.backdropPath}")
                        .fitCenter()
                        .centerCrop()
                        .into(image_view_activity_movie_detail_movie_backdrop)

                    supportActionBar?.title = it.data.title
                }
                is Resource.Error -> {
                    Toast.makeText(
                        this@MovieDetailActivity,
                        it.error?.errorDescription,
                        Toast.LENGTH_LONG
                    ).show()
                }
                Resource.InProgress -> {
                    Toast.makeText(
                        this@MovieDetailActivity,
                        "Load From Network...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}