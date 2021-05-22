package com.komangss.submissionjetpack.ui.tvshow.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_show_favorite.*

@AndroidEntryPoint
class TvShowFavoriteFragment : Fragment() {

    val viewModel by viewModels<TvShowFavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(activity != null) {

            val adapter = TvShowFavoriteAdapter()

            viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })

            with(fragment_tvshow_favorite_rv_tvshow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                setAdapter(adapter)
            }
        }
    }
}