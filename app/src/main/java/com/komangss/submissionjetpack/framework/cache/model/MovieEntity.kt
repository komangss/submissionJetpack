package com.komangss.submissionjetpack.framework.cache.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movie_entity")
@Parcelize
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val director: String,
    val description : String,
    val image : Int,
    val releaseDate : String,
    val rating : String
) : Parcelable