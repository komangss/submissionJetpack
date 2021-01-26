package com.komangss.submissionjetpack.framework.network.model

import com.google.gson.annotations.SerializedName

data class BelongsToCollection(
    val id : Int?,
    val name : String?,
    @SerializedName("poster_path")
    val posterPath : String?,
    @SerializedName("backdrop_path")
    val backdropPath : String?
)
