package com.komangss.submissionjetpack.framework.network.model

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("country_code")
    val countryCode : String,
    val name : String
)
