package com.komangss.submissionjetpack.business.domain.model

import com.komangss.submissionjetpack.framework.network.model.Genres
import com.komangss.submissionjetpack.framework.network.model.Network
import com.komangss.submissionjetpack.framework.network.model.SpokenLanguage
import java.util.*

data class TvShowDetail (
    val backdropPath: String?,
    val firstAirDate: String?,
    val genres: List<Genres?>,
    val homepage: String?,
    val id: Int?,
    val name: String?,
    val networks: List<Network>?,
    val description: String?,
    val popularity: Double?,
    val posterPath: String?,
    val spokenLanguages: List<SpokenLanguage>?,
    val status: String,
    val voteAverage: Double?,
    val voteCount: Int?
)