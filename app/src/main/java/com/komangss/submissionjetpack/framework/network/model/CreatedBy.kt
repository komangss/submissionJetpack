package com.komangss.submissionjetpack.framework.network.model

import com.google.gson.annotations.SerializedName

data class CreatedBy(
    val id : Int?,
    @SerializedName("credit_id")
    val creditId : String?,
    val name : String?,
    @SerializedName("profile_path")
    val profilePath : String?
)
