package com.komangss.submissionjetpack.ui.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.databinding.ItemsMovieAndTvshowBinding
import com.komangss.submissionjetpack.ui.tvshow.detail.TvShowDetailActivity
import com.komangss.submissionjetpack.ui.tvshow.detail.TvShowDetailActivity.Companion.EXTRA_TV_SHOW_ID

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    private val tvShowList = ArrayList<TvShow>()

    fun setTvShows(tvShowList: List<TvShow>) {
        this.tvShowList.clear()
        this.tvShowList.addAll(tvShowList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder(
            ItemsMovieAndTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = tvShowList.size

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(tvShowList[position])
    }

    class TvShowViewHolder(private val binding: ItemsMovieAndTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow) {
            binding.itemMovieTvshowTvItemTitle.text = tvShow.name
            binding.itemMovieTvshowTvDescription.text = tvShow.description

            binding.itemMovieTvshowRatingBar.rating = tvShow.voteAverage.toFloat() / 2

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/original/${tvShow.posterUrlPath}")
                .into(binding.itemMovieTvshowImageViewPoster)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, TvShowDetailActivity::class.java)
                intent.putExtra(EXTRA_TV_SHOW_ID, tvShow.id)
                binding.root.context.startActivity(intent)
            }
        }
    }
}