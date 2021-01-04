package com.komangss.submissionjetpack.ui.tvshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.BuildConfig
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.business.domain.model.TvShow
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
                item_movie_tvshow_tv_item_title.text = tvShow.title
                item_movie_tvshow_tv_description.text = tvShow.description

                Glide.with(context)
                    .load(resources.getIdentifier(tvShow.image, "drawable", BuildConfig.APPLICATION_ID))
                    .into(item_movie_tvshow_image_view_poster)

            }

//            TODO : Create Detail TvShow
        }
    }
}