package com.komangss.submissionjetpack.favorites.tvshow

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
import com.komangss.submissionjetpack.tvshow.detail.TvShowDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.fragment_tv_show_favorite.*
import javax.inject.Inject

@AndroidEntryPoint
class TvShowFavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel by viewModels<TvShowFavoriteViewModel> {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
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
            val adapter = TvShowFavoriteAdapter {
                val intent = Intent(activity, TvShowDetailActivity::class.java)
                intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, it.id)
                startActivity(intent)
            }

            viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, {
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