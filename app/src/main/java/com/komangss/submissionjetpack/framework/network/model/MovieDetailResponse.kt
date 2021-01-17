package com.komangss.submissionjetpack.framework.network.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    val adult : Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath : String?,
    @SerializedName("belongs_to_collection")
    val belongToCollection : BelongsToCollection?,
    val budget : Int?,
    val genres : List<Genres>?,
    val homepage : String?,
    val id : Int?,
    @SerializedName("imdb_id")
    val imdbId : String?,
    @SerializedName("original_language")
    val originalLanguage : String?,
    @SerializedName("overview")
    val description : String?,
    val popularity : Double?,
    @SerializedName("poster_path")
    val posterPath : String?,
    @SerializedName("production_companies")
    val productionCompanies : List<ProductionCompany>?,
    @SerializedName("production_countries")
    val productionCountries : List<ProductionCountry>?,
    @SerializedName("release_date")
    val releaseDate : String?,
    val revenue : Int?,
    val runtime : Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages : List<SpokenLanguage>?,
    val status : String?,
    val tagLine : String?,
    val title : String?,
    val video : Boolean?,
    @SerializedName("vote_average")
    val voteAverage : Double?,
    @SerializedName("vote_count")
    val voteCount : Int?
)