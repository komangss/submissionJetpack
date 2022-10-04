package com.komangss.submissionjetpack.favorites.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.core.domain.model.TvShow
import com.komangss.submissionjetpack.favorites.databinding.ItemsMovieAndTvshowBinding

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowFavoriteViewHolder =
        TvShowFavoriteViewHolder(
            ItemsMovieAndTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TvShowFavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TvShowFavoriteViewHolder(private val binding: ItemsMovieAndTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow?) {
            if (tvShow != null) {
                binding.itemMovieTvshowTvItemTitle.text = tvShow.name

                binding.itemMovieTvshowTvDescription.text = tvShow.description
                binding.itemMovieTvshowRatingBar.rating = tvShow.voteAverage.toFloat() / 2

                Glide.with(binding.root.context)
                    .load("https://image.tmdb.org/t/p/original/${tvShow.posterUrlPath}")
                    .into(binding.itemMovieTvshowImageViewPoster)


                binding.root.setOnClickListener {
                    onClickListener(tvShow)
                }
            }

        }
    }
}