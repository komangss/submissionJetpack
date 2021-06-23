package com.komangss.submissionjetpack.ui.movie.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.ui.movie.detail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_favorite.*

@AndroidEntryPoint
class MovieFavoriteFragment : Fragment() {

    val viewModel by viewModels<MovieFavoriteViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(activity != null) {
            val adapter = MovieFavoriteAdapter {
                val intent = Intent(activity, MovieDetailActivity::class.java)
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, it.id)
                startActivity(intent)

            }

            viewModel.getFavoriteMovies().observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })

            with(fragment_movie_favorite_rv_movie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                setAdapter(adapter)
            }
        }
    }
}