package com.komangss.submissionjetpack.favorites.tvshow

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
import com.komangss.submissionjetpack.favorites.databinding.FragmentTvShowFavoriteBinding
import com.komangss.submissionjetpack.tvshow.detail.TvShowDetailActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class TvShowFavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel by viewModels<TvShowFavoriteViewModel> {
        factory
    }
    private var _binding: FragmentTvShowFavoriteBinding? = null
    private val binding get() = _binding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    FavoritesModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvShowFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle ?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val adapter = TvShowFavoriteAdapter {
                val intent = Intent(activity, TvShowDetailActivity::class.java)
                intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, it.id)
                startActivity(intent)
            }

            viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, {
                if(it.size > 0) {
                    binding.fragmentTvshowFavoriteRvTvshow.visibility = View.VISIBLE
                    binding.animationView.visibility = View.GONE
                } else {
                    Toast.makeText(activity, "Your Favorite's Tv Show is Empty", Toast.LENGTH_SHORT).show()
                    binding.fragmentTvshowFavoriteRvTvshow.visibility = View.GONE
                    binding.animationView.visibility = View.VISIBLE
                }
                adapter.submitList(it)
            })

            with(binding.fragmentTvshowFavoriteRvTvshow) {
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