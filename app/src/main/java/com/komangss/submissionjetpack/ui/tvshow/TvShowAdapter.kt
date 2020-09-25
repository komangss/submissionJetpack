package com.komangss.submissionjetpack.ui.tvshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import kotlinx.android.synthetic.main.items_movie_and_tvshow.view.*

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    private val tvShowList = ArrayList<TvShowEntity>()

    fun setTvShows(tvShowList : List<TvShowEntity>) {
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
        fun bind(tvShow : TvShowEntity) {
            with(itemView) {
                item_movie_tvshow_tv_item_title.text = tvShow.title
                item_movie_tvshow_tv_description.text = tvShow.description

                Glide.with(context)
                    .load(tvShow.image)
                    .into(item_movie_tvshow_image_view_poster)
            }

//            TODO : Create Detail TvShow
        }
    }
}