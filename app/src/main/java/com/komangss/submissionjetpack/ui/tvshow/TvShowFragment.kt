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
import com.komangss.submissionjetpack.databinding.FragmentTvShowBinding
import com.komangss.submissionjetpack.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    val viewModel by viewModels<TvShowViewModel>()
    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvShowAdapter = TvShowAdapter()

        viewModel.tvShowList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.fragmentTvshowProgressBar.visibility = View.GONE
                    tvShowAdapter.setTvShows(it.data)
                    tvShowAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    binding.fragmentTvshowProgressBar.visibility = View.GONE
                    Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
                }
                Resource.InProgress -> {
                    binding.fragmentTvshowProgressBar.visibility = View.VISIBLE
                    Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                }
            }
        })

        with(binding.fragmentTvshowRvTvshow) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}