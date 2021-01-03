package com.komangss.submissionjetpack.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id : Int,
    val title : String,
    val director: String,
    val description : String,
    val imageUrl : String,
    val releaseDate : String,
    val rating : String
) : Parcelable