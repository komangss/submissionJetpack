package com.komangss.submissionjetpack.ui.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.BuildConfig
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.ui.tvshow.detail.TvShowDetailActivity
import com.komangss.submissionjetpack.ui.tvshow.detail.TvShowDetailActivity.Companion.EXTRA_TV_SHOW_ID
import kotlinx.android.synthetic.main.items_movie_and_tvshow.view.*

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    private val tvShowList = ArrayList<TvShow>()

    fun setTvShows(tvShowList : List<TvShow>) {
        this.tvShowList.clear()
        this.tvShowList.addAll(tvShowList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_movie_and_tvshow, parent, false)
        )
    }

    override fun getItemCount(): Int = tvShowList.size

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(tvShowList[position])
    }

    class TvShowViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow : TvShow) {
            with(itemView) {
                item_movie_tvshow_tv_item_title.text = tvShow.name
                item_movie_tvshow_tv_description.text = tvShow.description

                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original/${tvShow.posterUrlPath}")
                    .into(item_movie_tvshow_image_view_poster)

                setOnClickListener {
                    val intent = Intent(context, TvShowDetailActivity::class.java)
                    intent.putExtra(EXTRA_TV_SHOW_ID, tvShow.id)
                    context.startActivity(intent)
                }
            }
        }
    }
}