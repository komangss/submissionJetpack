package com.komangss.submissionjetpack.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieEntity(
    val id : Int,
    val title : String,
    val director: String,
    val description : String,
    val image : Int,
    val releaseDate : String,
    val rating : String
) : Parcelable