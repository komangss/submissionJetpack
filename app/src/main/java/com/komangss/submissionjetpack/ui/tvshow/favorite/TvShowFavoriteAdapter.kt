package com.komangss.submissionjetpack.ui.tvshow.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.business.domain.model.TvShow
import kotlinx.android.synthetic.main.items_movie_and_tvshow.view.*

class TvShowFavoriteAdapter(
    private val onClickListener: (tvShow: TvShow) -> Unit
) : PagedListAdapter<TvShow, TvShowFavoriteAdapter.TvShowFavoriteViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                oldItem == newItem
        }
    }

    inner class TvShowFavoriteViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(tvShow: TvShow?) {
            with(v) {
                if (tvShow != null) {
                    item_movie_tvshow_tv_item_title.text = tvShow.name

                    item_movie_tvshow_tv_description.text = tvShow.description
                    item_movie_tvshow_rating_bar.rating = tvShow.voteAverage.toFloat() / 2

                    Glide.with(context)
                        .load("https://image.tmdb.org/t/p/original/${tvShow.posterUrlPath}")
                        .into(item_movie_tvshow_image_view_poster)


                    setOnClickListener {
                        onClickListener(tvShow)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowFavoriteViewHolder =
        TvShowFavoriteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_movie_and_tvshow, parent, false)
        )


    override fun onBindViewHolder(holder: TvShowFavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}