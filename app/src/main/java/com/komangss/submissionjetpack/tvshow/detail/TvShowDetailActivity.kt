package com.komangss.submissionjetpack.tvshow.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.databinding.ActivityTvShowDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowDetailActivity : AppCompatActivity() {

    val viewModel by viewModels<TvShowDetailViewModel>()
    private var _binding: ActivityTvShowDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.tvshow)

        val tvShowId = intent.getIntExtra(EXTRA_TV_SHOW_ID, -1)

        viewModel.setTvShowId(tvShowId)

        viewModel.tvShow.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val tvShow = it.data
                    binding.tvActivityTvShowDetailTvShowTitle.text = tvShow.name
                    binding.tvActivityTvShowDetailTvShowDescription.text = tvShow.description
                    val voteAverage = tvShow.voteAverage.div(2)
                    binding.itemTvShowTvshowRatingBar.rating = voteAverage.toFloat()
                    binding.tvActivityTvShowDetailTvShowRating.text = "$voteAverage / 5"
                    supportActionBar?.title = tvShow.name

                    Glide.with(this@TvShowDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${tvShow.posterUrlPath}")
                        .into(binding.imageViewActivityTvShowDetailTvShowBackdrop)

                    Glide.with(this@TvShowDetailActivity)
                        .load("https://image.tmdb.org/t/p/original/${tvShow.backdropUrlPath}")
                        .fitCenter()
                        .centerCrop()
                        .into(binding.imageViewActivityTvShowDetailTvShowBackdrop)

                    setFavorite(tvShow.isFavorite)

                    binding.fabActivityTvShowDetailFavorite.setOnClickListener {
                        val boolean = tvShow.isFavorite
                        tvShow.isFavorite = !boolean
                        setFavorite(tvShow.isFavorite)
                        if (tvShow.isFavorite) {
                            Toast.makeText(
                                this@TvShowDetailActivity,
                                "Added to Favorite List",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@TvShowDetailActivity,
                                "Removed From Favorite List",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        lifecycleScope.launch {
                            viewModel.setFavorite(tvShow)
                        }
                    }
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
                        "Loading...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


    }

    private fun setFavorite(state: Boolean) {
        if (state) {
            binding.fabActivityTvShowDetailFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.fabActivityTvShowDetailFavorite.setImageResource(R.drawable.ic_broken_heart)
        }

    }

    companion object {
        const val EXTRA_TV_SHOW_ID = "extra_tv_show_id"
    }

}