package com.komangss.submissionjetpack.framework.network.model

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName : String?,
    @SerializedName("iso_639_1")
    val countryCodeName : String?,
    val name : String?
)
