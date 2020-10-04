package com.komangss.submissionjetpack.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
            viewModel.getMovies().observe(viewLifecycleOwner, {
////                when (it.status) {
//////                    TODO : Fix what happen when data received
////                    Status.SUCCESS -> {
////                        fragment_movie_progress_bar.visibility = View.GONE
////                        it.data?.let { movieList -> movieAdapter.setMovies(movieList) }
////                        movieAdapter.notifyDataSetChanged()
////                    }
////                    Status.ERROR -> {
////
////                    }
////                    Status.EMPTY -> {
////
////                    }
//                }


            })

            with(fragment_movie_rv_movie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }

        Log.d(
            "MyTag",
            "alita : ${R.drawable.poster_alita}\n" +
                    "aquaman : ${R.drawable.poster_aquaman}\n" +
                    "bohemian : ${R.drawable.poster_bohemian} \n" +
                    "creed 2 : ${R.drawable.poster_creed} \n" +
                    "the crimes : ${R.drawable.poster_crimes} \n" +
                    "glass : ${R.drawable.poster_glass} \n" +
                    "how to : ${R.drawable.poster_how_to_train} \n" +
                    "avenger : ${R.drawable.poster_infinity_war} \n" +
                    "master : ${R.drawable.poster_master_z} \n" +
                    "mortal : ${R.drawable.poster_mortal_engines} \n" +
                    "overlord : ${R.drawable.poster_overlord} \n" +
                    "ralph : ${R.drawable.poster_ralph} \n" +
                    "robin : ${R.drawable.poster_robin_hood} \n" +
                    "serenity : ${R.drawable.poster_serenity} \n" +
                    "spider : ${R.drawable.poster_spiderman} \n" +
                    "t34 : ${R.drawable.poster_t34} \n"
        )
    }
}
