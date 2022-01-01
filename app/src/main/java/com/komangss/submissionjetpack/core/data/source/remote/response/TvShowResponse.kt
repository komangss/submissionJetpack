package com.komangss.submissionjetpack.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponse (
    @SerializedName("backdrop_path")
    val backdropUrlPath: String?,
    @SerializedName("first_air_date")
    val releaseDate: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originalCountry : List<String>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("overview")
    val description: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterUrlPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)