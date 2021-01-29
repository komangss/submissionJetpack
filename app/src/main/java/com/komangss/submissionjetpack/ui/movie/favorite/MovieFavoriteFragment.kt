package com.komangss.submissionjetpack.ui.movie.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie_favorite.*

class MovieFavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieFavoriteViewModel::class.java]

            val adapter = MovieFavoriteAdapter()

            viewModel.getFavoriteMovies().observe(viewLifecycleOwner, {
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