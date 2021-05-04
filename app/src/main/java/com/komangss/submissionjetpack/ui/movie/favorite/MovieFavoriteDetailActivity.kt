package com.komangss.submissionjetpack.ui.movie.favorite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.favorite.FavoriteActivity
import com.komangss.submissionjetpack.ui.movie.detail.MovieDetailViewModel
import com.komangss.submissionjetpack.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.items_movie_and_tvshow.view.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class MovieFavoriteDetailActivity : AppCompatActivity() {

    val viewModel: MovieDetailViewModel by viewModels()
    private var isFav = false

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.title = getString(R.string.movie)

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)

        viewModel.setMovieId(movieId)

        viewModel.movie.observe(this, {
            when (it) {
                is Resource.Success -> {
                    val movie = it.data
                    tv_activity_movie_detail_movie_title.text = movie.title
                    tv_activity_movie_detail_movie_description.text = movie.description
                    val voteAverage = movie.voteAverage.div(2).toFloat()
                    item_movie_tvshow_rating_bar.rating = voteAverage
                    tv_activity_movie_detail_movie_rating.text = "$voteAverage / 5"

                    Glide.with(this@MovieFavoriteDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${movie.posterUrlPath}")
                        .into(image_view_activity_movie_detail_movie_poster)

                    Glide.with(this@MovieFavoriteDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${movie.backdropUrlPath}")
                        .fitCenter()
                        .centerCrop()
                        .into(image_view_activity_movie_detail_movie_backdrop)

                    supportActionBar?.title = movie.title

                    isFav = movie.isFavorite

                    setFavorite()

                    fab__activity_movie_detail_favorite.setOnClickListener {
                        lifecycleScope.launch {
                            viewModel.setFavorite(movie)
                            withContext(Dispatchers.Main) {
                                finish()
                                startActivity(Intent(this@MovieFavoriteDetailActivity, FavoriteActivity::class.java))
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(
                        this@MovieFavoriteDetailActivity,
                        it.error?.errorDescription,
                        Toast.LENGTH_LONG
                    ).show()
                }
                Resource.InProgress -> {
                    Toast.makeText(
                        this@MovieFavoriteDetailActivity,
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

    override fun onBackPressed() {
        startActivity(Intent(this@MovieFavoriteDetailActivity, FavoriteActivity::class.java))
        finish()
        super.onBackPressed()
    }

}