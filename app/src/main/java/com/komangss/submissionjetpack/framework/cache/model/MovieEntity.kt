package com.komangss.submissionjetpack.framework.cache.model

// TODO : Make this entity using room's entity

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