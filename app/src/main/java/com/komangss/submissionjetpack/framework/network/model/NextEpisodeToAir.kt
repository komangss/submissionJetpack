package com.komangss.submissionjetpack.framework.network.model

import com.google.gson.annotations.SerializedName

data class NextEpisodeToAir(
    @SerializedName("air_date")
    val airDate : String?,
    @SerializedName("episode_number")
    val episodeNumber : Int?,
    val id : Int?,
    val name : String?,
    @SerializedName("overview")
    val description : String?,
    @SerializedName("production_code")
    val productionCode : String?,
    @SerializedName("season_number")
    val seasonNumber : Int?,
    @SerializedName("still_path")
    val stillPath : String?,
    @SerializedName("vote_average")
    val voteAverage : Double?,
    @SerializedName("vote_count")
    val voteCount : Int?
)
