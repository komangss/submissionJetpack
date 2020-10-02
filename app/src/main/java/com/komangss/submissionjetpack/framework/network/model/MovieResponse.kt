package com.komangss.submissionjetpack.framework.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    val id : Int,
    val title : String,
    val director: String,
    val description : String,
    val image : String,
    val releaseDate : String,
    val rating : String
) : Parcelable {
    constructor() : this(0, "", "", "", "", "","")
}