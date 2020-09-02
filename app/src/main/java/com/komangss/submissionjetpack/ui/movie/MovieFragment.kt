package com.komangss.submissionjetpack.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie.*

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            val movieAdapter = MovieAdapter()
            fragment_movie_progress_bar.visibility = View.VISIBLE
            viewModel.getMovies().observe(viewLifecycleOwner, Observer {
                fragment_movie_progress_bar.visibility = View.GONE
                movieAdapter.setMovies(it)
                movieAdapter.notifyDataSetChanged()
            })

            with(fragment_movie_rv_movie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }

//        Log.d("MyTag",
//                "alita : ${R.drawable.poster_alita}\n" +
//                "aquaman : ${R.drawable.poster_aquaman}\n" +
//                "bohemian : ${R.drawable.poster_bohemian} \n" +
//                "creed 2 : ${R.drawable.poster_creed} \n" +
//                "the crimes : ${R.drawable.poster_crimes} \n"
//        )
    }
}
