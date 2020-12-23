package com.komangss.submissionjetpack.framework.cache.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movie_entity")
@Parcelize
data class MovieEntity(
    val popularity: Double,
    val voteCount: Int,
    val isVideo: Boolean,
    val posterUrlPath: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val backdropUrlPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val genreIds: String,
    val title: String,
    val voteAverage: Double,
    val description: String,
    val releaseDate: String
) : Parcelable