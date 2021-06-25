package com.komangss.submissionjetpack.ui.movie.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.databinding.ItemsMovieAndTvshowBinding

class MovieFavoriteAdapter(
  private val onClickListener: (movie : Movie) -> Unit
) : PagedListAdapter<Movie, MovieFavoriteAdapter.MovieFavoriteViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavoriteViewHolder =
        MovieFavoriteViewHolder(
            ItemsMovieAndTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MovieFavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieFavoriteViewHolder(private val binding: ItemsMovieAndTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?) {
            if (movie != null) {
                binding.itemMovieTvshowTvItemTitle.text = movie.title

                binding.itemMovieTvshowTvDescription.text = movie.description
                binding.itemMovieTvshowRatingBar.rating = movie.voteAverage.toFloat() / 2

                Glide.with(binding.root.context)
                    .load("https://image.tmdb.org/t/p/original/${movie.posterUrlPath}")
                    .into(binding.itemMovieTvshowImageViewPoster)

                binding.root.setOnClickListener {
                    onClickListener(movie)
                }
            }
        }
    }
}