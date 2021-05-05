package com.komangss.submissionjetpack.ui.movie.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.viewmodel.ViewModelFactory
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {

    private var isFav = false

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.title = getString(R.string.movie)

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]

        viewModel.setMovieId(movieId)

        viewModel.movie.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    val movie = it.data
                    tv_activity_movie_detail_movie_title.text = movie.title
                    tv_activity_movie_detail_movie_description.text = movie.description
                    val voteAverage = movie.voteAverage.div(2).toFloat()
                    item_movie_tvshow_rating_bar.rating = voteAverage
                    tv_activity_movie_detail_movie_rating.text = "$voteAverage / 5"

                    Glide.with(this@MovieDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${movie.posterUrlPath}")
                        .into(image_view_activity_movie_detail_movie_poster)

                    Glide.with(this@MovieDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${movie.backdropUrlPath}")
                        .fitCenter()
                        .centerCrop()
                        .into(image_view_activity_movie_detail_movie_backdrop)

                    supportActionBar?.title = movie.title

                    isFav = movie.isFavorite

                    setFavorite()

                    fab__activity_movie_detail_favorite.setOnClickListener {
                        isFav = !isFav
                        setFavorite()
                        if (isFav) {
                            Toast.makeText(
                                this@MovieDetailActivity,
                                "Added to Favorite List",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@MovieDetailActivity,
                                "Removed From Favorite List",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        lifecycleScope.launch {
                            viewModel.setFavorite(movie)
                        }
                    }
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
                        "Loading...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun setFavorite() {
        if(isFav) {
            fab__activity_movie_detail_favorite.setImageResource(R.drawable.ic_favorite)
        } else {
            fab__activity_movie_detail_favorite.setImageResource(R.drawable.ic_broken_heart)
        }

    }
    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}