package com.komangss.submissionjetpack.business.domain.model

data class Movie(
    val popularity: Double,
    val voteCount: Int,
    val isVideo: Boolean,
    val posterUrlPath: String,
    val id: Int,
    val backdropUrlPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val genreIds: List<Int>?,
    val title: String,
    val voteAverage: Double,
    val description: String,
    val releaseDate: String,
    var isFavorite : Boolean = false
)