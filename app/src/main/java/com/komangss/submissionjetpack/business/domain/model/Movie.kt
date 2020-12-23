package com.komangss.submissionjetpack.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val popularity: Double,
    val voteCount: Int,
    val isVideo: Boolean,
    val posterUrlPath: String,
    val id: Int,
    val backdropUrlPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val genreIds: List<Int>?, // TODO : Research how to create and find the genre with the id using custom class
    val title: String,
    val voteAverage: Double,
    val description: String,
    val releaseDate: String
) : Parcelable