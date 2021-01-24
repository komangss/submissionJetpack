package com.komangss.submissionjetpack.framework.network.model

import com.google.gson.annotations.SerializedName

data class Network(
    val name : String?,
    val id : Int?,
    @SerializedName("logo_path")
    val logoPath : String?,
    @SerializedName("origin_country")
    val originCountry : String?
)
