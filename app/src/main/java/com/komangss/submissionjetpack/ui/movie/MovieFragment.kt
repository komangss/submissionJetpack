package com.komangss.submissionjetpack.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.viewmodel.ViewModelFactory
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

class MovieFragment : Fragment() {

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
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            val movieAdapter = MovieAdapter()
            viewModel.movieList.observe(viewLifecycleOwner, {
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
