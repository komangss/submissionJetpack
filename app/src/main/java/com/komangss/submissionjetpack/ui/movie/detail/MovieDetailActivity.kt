package com.komangss.submissionjetpack.ui.movie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]

        viewModel.detailMovie(movieId).observe(this, {
            tv_activity_movie_detail_movie_title.text = it.title
            tv_activity_movie_detail_movie_description.text = it.description
            tv_activity_movie_detail_movie_rating_movie.text = it.rating
//            Glide.with(this@MovieDetailActivity)
//                .load(ResourcesCompat.getDrawable(it.image))
//                .into(image_view_activity_movie_detail_movie_poster)
        })
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}