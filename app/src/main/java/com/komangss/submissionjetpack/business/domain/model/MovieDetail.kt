package com.komangss.submissionjetpack.business.domain.model

import com.google.gson.annotations.SerializedName
import com.komangss.submissionjetpack.framework.network.model.*

data class MovieDetail(
    val adult : Boolean,
    val backdropPath : String,
    val genres : List<Genres>,
    val id : Int,
    val originalLanguage : String,
    val description : String,
    val popularity : Double,
    val releaseDate : String,
    val status : String,
    val tagLine : String,
    val title : String,
    val video : Boolean,
    val voteAverage : Double,
    val voteCount : Int
)
