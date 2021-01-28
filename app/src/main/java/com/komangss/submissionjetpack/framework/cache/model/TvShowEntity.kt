package com.komangss.submissionjetpack.framework.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_entity")
data class TvShowEntity (
    val backdropUrlPath: String,
    val releaseDate: String,
    val genreIds: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val originalCountry : String,
    val originalLanguage: String,
    val originalName: String,
    val description: String,
    val popularity: Double,
    val posterUrlPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    var isFavorite : Boolean = false
)