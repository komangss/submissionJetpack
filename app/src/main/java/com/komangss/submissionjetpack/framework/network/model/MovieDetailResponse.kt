package com.komangss.submissionjetpack.framework.network.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    val adult : Boolean,
    val backdropPath : String,
    val belongToCollection : BelongsToCollection,
    val budget : Int,
    val genres : List<Genres>,
    val homepage : String,
    val id : Int,
    val tmdb_id : String,
    val originalLanguage : String,
    @SerializedName("overview")
    val description : String,
    val popularity : Double,
    val productionCompanies : List<ProductionCompany>,
    val productionCountries : List<ProductionCountry>,
    val releaseDate : String,
    val revenue : Int,
    val runtime : Int,
    val spokenLanguages : List<SpokenLanguage>,
    val status : String,
    val tagLine : String,
    val title : String,
    val video : Boolean,
    val voteAverage : Double,
    val voteCount : Int
)