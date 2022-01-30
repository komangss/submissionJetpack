package com.komangss.submissionjetpack.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.core.domain.model.Movie
import com.komangss.submissionjetpack.databinding.ItemsMovieAndTvshowBinding
import com.komangss.submissionjetpack.movie.detail.MovieDetailActivity
import com.komangss.submissionjetpack.movie.detail.MovieDetailActivity.Companion.EXTRA_MOVIE_ID

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movieList = ArrayList<Movie>()

    fun setMovies(newMovieList: List<Movie>) {
        val diff = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return movieList[oldItemPosition].id == newMovieList[newItemPosition].id
            }
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return movieList[oldItemPosition] == newMovieList[newItemPosition]
            }
            override fun getOldListSize() = movieList.size
            override fun getNewListSize() = newMovieList.size
        }
        val diffResult = DiffUtil.calculateDiff(diff)
        this.movieList.clear()
        this.movieList.addAll(movieList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemsMovieAndTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class MovieViewHolder(private val binding: ItemsMovieAndTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.itemMovieTvshowTvItemTitle.text = movie.title
            binding.itemMovieTvshowTvDescription.text = movie.description

            binding.itemMovieTvshowRatingBar.rating = movie.voteAverage.toFloat() / 2

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/original/${movie.posterUrlPath}")
                .into(binding.itemMovieTvshowImageViewPoster)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, MovieDetailActivity::class.java)
                intent.putExtra(EXTRA_MOVIE_ID, movie.id)
                binding.root.context.startActivity(intent)
            }
        }
    }

}