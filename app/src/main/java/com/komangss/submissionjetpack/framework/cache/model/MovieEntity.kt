package com.komangss.submissionjetpack.framework.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entity")
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
)