package com.komangss.submissionjetpack.framework.network.model

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val countryCode : String,
    val name : String
)
