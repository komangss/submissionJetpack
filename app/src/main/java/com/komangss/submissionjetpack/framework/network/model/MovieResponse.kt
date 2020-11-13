package com.komangss.submissionjetpack.framework.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("video")
    val isVideo: Boolean,
    @SerializedName("poster_path")
    val posterUrlPath: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("backdrop_path")
    val backdropUrlPath: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?, // TODO : Research how to create and find the genre with the id using custom class
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("overview")
    val description: String,
    @SerializedName("release_date")
    val releaseDate: String
) : Parcelable {
    constructor() : this (
        0.0,
        0,
        false,
        "",
        0,
        "",
        "",
        "",
        null,
        "",
    0.0,
    "",
    ""
    )
}