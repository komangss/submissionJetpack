package com.komangss.submissionjetpack.ui.movie.favorite

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.ui.movie.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.items_movie_and_tvshow.view.*

class MovieFavoriteAdapter :
    PagedListAdapter<Movie, MovieFavoriteAdapter.MovieFavoriteViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    inner class MovieFavoriteViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(movie: Movie?) {
            with(v) {
                if (movie != null) {
                    item_movie_tvshow_tv_item_title.text = movie.title

                    item_movie_tvshow_tv_description.text = movie.description
                    item_movie_tvshow_rating_bar.rating = movie.voteAverage.toFloat() / 2

                    Glide.with(context)
                        .load("https://image.tmdb.org/t/p/original/${movie.posterUrlPath}")
                        .into(item_movie_tvshow_image_view_poster)

                    setOnClickListener {
                        val intent = Intent(context, MovieDetailActivity::class.java)
                        intent.putExtra(MovieFavoriteDetailActivity.EXTRA_MOVIE_ID, movie.id)
                        context.startActivity(intent)
                         (context as Activity).finish()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavoriteViewHolder =
        MovieFavoriteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_movie_and_tvshow, parent, false)
        )

    override fun onBindViewHolder(holder: MovieFavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}