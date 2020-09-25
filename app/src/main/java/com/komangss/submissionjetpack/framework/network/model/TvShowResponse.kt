package com.komangss.submissionjetpack.framework.network.model

data class TvShowResponse (
    val id : Int,
    val title : String,
    val description : String,
    val image : Int,
    val releaseDate : String,
    val rating : String
)