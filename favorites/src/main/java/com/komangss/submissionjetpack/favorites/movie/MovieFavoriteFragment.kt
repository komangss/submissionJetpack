package com.komangss.submissionjetpack.favorites.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.di.FavoritesModuleDependencies
import com.komangss.submissionjetpack.favorites.DaggerFavoriteComponent
import com.komangss.submissionjetpack.favorites.ViewModelFactory
import com.komangss.submissionjetpack.favorites.databinding.FragmentMovieFavoriteBinding
import com.komangss.submissionjetpack.movie.detail.MovieDetailActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class MovieFavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel by viewModels<MovieFavoriteViewModel> {
        factory
    }

    private var _binding: FragmentMovieFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoritesModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            val adapter = MovieFavoriteAdapter {
                val intent = Intent(activity, MovieDetailActivity::class.java)
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, it.id)
                startActivity(intent)

            }

            viewModel.getFavoriteMovies().observe(viewLifecycleOwner) {
                if (it.size > 0) {
                    binding.fragmentMovieFavoriteRvMovie.visibility = View.VISIBLE
                    binding.animationView.visibility = View.GONE
                } else {
                    Toast.makeText(activity, "Your Favorite's Movie is Empty", Toast.LENGTH_SHORT)
                        .show()
                    binding.fragmentMovieFavoriteRvMovie.visibility = View.GONE
                    binding.animationView.visibility = View.VISIBLE
                }
                adapter.submitList(it)
            }

            with(binding.fragmentMovieFavoriteRvMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                setAdapter(adapter)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}