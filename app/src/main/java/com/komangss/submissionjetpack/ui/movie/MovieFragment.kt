package com.komangss.submissionjetpack.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.databinding.FragmentMovieBinding
import com.komangss.submissionjetpack.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class MovieFragment : Fragment() {

    val viewModel by viewModels<MovieViewModel>()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieAdapter = MovieAdapter()

        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.fragmentMovieProgressBar.visibility = View.GONE
                    movieAdapter.setMovies(it.data)
                }
                is Resource.Error -> {
                    binding.fragmentMovieProgressBar.visibility = View.GONE
                    Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
                }
                Resource.InProgress -> {
                    binding.fragmentMovieProgressBar.visibility = View.VISIBLE
                    Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                }
            }
        })

        with(binding.fragmentMovieRvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
