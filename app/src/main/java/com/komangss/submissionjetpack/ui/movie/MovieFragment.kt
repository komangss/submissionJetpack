package com.komangss.submissionjetpack.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class MovieFragment : Fragment() {
    val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_movie,
            container,
            false
        )
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val movieAdapter = MovieAdapter()
            viewModel.movieList.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Resource.Success -> {
                        fragment_movie_progress_bar.visibility = View.GONE
                        movieAdapter.setMovies(it.data)
                    }
                    is Resource.Error -> {
                        fragment_movie_progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
                    }
                    Resource.InProgress -> {
                        fragment_movie_progress_bar.visibility = View.VISIBLE
                        Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                    }
                }
            })

            with(fragment_movie_rv_movie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }
}
