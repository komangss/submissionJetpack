package com.komangss.submissionjetpack.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.core.data.Resource
import com.komangss.submissionjetpack.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    val viewModel by viewModels<MovieViewModel>()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val movieAdapter = MovieAdapter()
            viewModel.movieList.observe(viewLifecycleOwner) {
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
            }

            with(binding.fragmentMovieRvMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
