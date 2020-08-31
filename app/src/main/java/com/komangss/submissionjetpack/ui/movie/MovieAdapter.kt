package com.komangss.submissionjetpack.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.data.source.local.entity.MovieEntity
import kotlinx.android.synthetic.main.items_movie_and_tvshow.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movieList = ArrayList<MovieEntity>()

    fun setMovies(movieList : List<MovieEntity>) {
        this.movieList.clear()
        this.movieList.addAll(movieList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_movie_and_tvshow, parent, false)
        )
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie : MovieEntity) {
            with(itemView) {
                item_movie_tvshow_tv_item_title.text = movie.title
                item_movie_tvshow_tv_description.text = movie.description

                Glide.with(context)
                    .load(movie.image)
                    .into(item_movie_tvshow_image_view_poster)
            }

//            TODO : Create Detail Movie
        }
    }
}