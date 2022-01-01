package com.komangss.submissionjetpack.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.komangss.submissionjetpack.R
import com.komangss.submissionjetpack.core.data.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class TvShowFragment : Fragment() {
    val viewModel by viewModels<TvShowViewModel>()
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

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {

            val tvShowAdapter = TvShowAdapter()

            viewModel.tvShowList.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Resource.Success -> {
                        tvShowAdapter.setTvShows(it.data)
                        tvShowAdapter.notifyDataSetChanged()
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
                    }
                    Resource.InProgress -> {
                    }
                }
            })

            with(fragment_tvshow_rv_tvshow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
        }
    }
}