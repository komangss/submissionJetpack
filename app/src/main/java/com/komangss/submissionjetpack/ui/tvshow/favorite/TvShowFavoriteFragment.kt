package com.komangss.submissionjetpack.ui.tvshow.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.databinding.FragmentTvShowFavoriteBinding
import com.komangss.submissionjetpack.ui.tvshow.detail.TvShowDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowFavoriteFragment : Fragment() {

    val viewModel by viewModels<TvShowFavoriteViewModel>()
    private var _binding: FragmentTvShowFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvShowFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TvShowFavoriteAdapter {
            val intent = Intent(activity, TvShowDetailActivity::class.java)
            intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, it.id)
            startActivity(intent)
        }

        viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        with(binding.fragmentTvshowFavoriteRvTvshow) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            setAdapter(adapter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}