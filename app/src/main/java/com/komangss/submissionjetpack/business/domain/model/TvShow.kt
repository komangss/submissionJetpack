package com.komangss.submissionjetpack.business.domain.model

data class TvShow (
    val backdropUrlPath: String,
    val releaseDate: String,
    val genreIds: List<Int>?,
    val id: Int,
    val name: String,
    val originalCountry : List<String>?,
    val originalLanguage: String,
    val originalName: String,
    val description: String,
    val popularity: Double,
    val posterUrlPath: String,
    val voteAverage: Double,
    val voteCount: Int
)