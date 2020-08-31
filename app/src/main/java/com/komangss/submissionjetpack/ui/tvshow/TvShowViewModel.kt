package com.komangss.submissionjetpack.ui.tvshow

import androidx.lifecycle.ViewModel
import com.komangss.submissionjetpack.data.TvShowEntity
import com.komangss.submissionjetpack.utils.DataGenerator

class TvShowViewModel : ViewModel() {
    fun getTvShows() : List<TvShowEntity> = DataGenerator.generateDummyTvShows()
}