package com.komangss.submissionjetpack.ui.movie.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.databinding.ActivityMovieDetailBinding
import com.komangss.submissionjetpack.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private var _binding: ActivityMovieDetailBinding? = null
    private val binding get() = _binding!!

    val viewModel by viewModels<MovieDetailViewModel>()

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.movie)

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)

        viewModel.setMovieId(movieId)

        viewModel.movie.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    val movie = it.data
                    binding.tvActivityMovieDetailMovieTitle.text = movie.title
                    binding.tvActivityMovieDetailMovieDescription.text = movie.description
                    val voteAverage = movie.voteAverage.div(2)
                    binding.itemMovieTvshowRatingBar.rating = voteAverage.toFloat()
                    binding.tvActivityMovieDetailMovieRating.text = "$voteAverage / 5"

                    Glide.with(this@MovieDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${movie.posterUrlPath}")
                        .into(binding.imageViewActivityMovieDetailMoviePoster)

                    Glide.with(this@MovieDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${movie.backdropUrlPath}")
                        .fitCenter()
                        .centerCrop()
                        .into(binding.imageViewActivityMovieDetailMovieBackdrop)

                    supportActionBar?.title = movie.title

                    setFavorite(movie.isFavorite)

                    binding.fabActivityMovieDetailFavorite.setOnClickListener {
                        val boolean = movie.isFavorite
                        movie.isFavorite = !boolean
                        setFavorite(movie.isFavorite)
                        if (movie.isFavorite) {
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

    private fun setFavorite(state : Boolean) {
        if(state) {
            binding.fabActivityMovieDetailFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.fabActivityMovieDetailFavorite.setImageResource(R.drawable.ic_broken_heart)
        }

    }
    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}