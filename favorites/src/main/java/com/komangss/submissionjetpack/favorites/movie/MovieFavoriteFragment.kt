package com.komangss.submissionjetpack.favorites.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.di.FavoritesModuleDependencies
import com.komangss.submissionjetpack.favorites.DaggerFavoriteComponent
import com.komangss.submissionjetpack.favorites.R
import com.komangss.submissionjetpack.favorites.ViewModelFactory
import com.komangss.submissionjetpack.movie.detail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieFavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel by viewModels<MovieFavoriteViewModel> {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoritesModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        if (activity != null) {
            val adapter = MovieFavoriteAdapter {
                val intent = Intent(activity, MovieDetailActivity::class.java)
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, it.id)
                startActivity(intent)

            }

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