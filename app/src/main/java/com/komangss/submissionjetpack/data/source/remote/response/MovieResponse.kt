package com.komangss.submissionjetpack.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    val id : Int,
    val title : String,
    val director: String,
    val description : String,
    val image : Int,
    val releaseDate : String,
    val rating : String
) : Parcelable {
    constructor() : this(0, "", "", "", 0, "","")
}