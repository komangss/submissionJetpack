package com.komangss.submissionjetpack.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResultResponse (
    @SerializedName("page")
    val page : Int?,
    @SerializedName("total_results")
    val totalResults : Int?,
    @SerializedName("total_pages")
    val totalPages : Int?,
    @SerializedName("results")
    val results : List<MovieResponse>?
)