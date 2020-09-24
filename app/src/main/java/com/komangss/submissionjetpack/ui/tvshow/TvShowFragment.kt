package com.komangss.submissionjetpack.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.utils.DataGenerator
import kotlinx.android.synthetic.main.fragment_tv_show.*


class TvShowFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_tv_show,
            container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {

//            val viewModel = ViewModelProvider(this).get(TvShowViewModel::class.java)
//            val tvShows = viewModel.g()

            val tvShowAdapter = TvShowAdapter()
//            tvShowAdapter.setTvShows(tvShows)

            with(fragment_tvshow_rv_tvshow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
        }
    }
}