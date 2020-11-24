package com.komangss.submissionjetpack.framework.network.mappers

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.util.ResponseMapper
import com.komangss.submissionjetpack.framework.network.model.MovieResponse

class MovieNetworkMapper : ResponseMapper<MovieResponse, Movie> {
    override fun mapFromResponse(response: MovieResponse): Movie {
        return Movie(
            popularity = response.popularity,
            voteCount = response.voteCount,
            isVideo = response.isVideo,
            posterUrlPath = response.posterUrlPath,
            id = response.id,
            backdropUrlPath = response.backdropUrlPath,
            originalLanguage = response.originalLanguage,
            originalTitle = response.originalTitle,
            genreIds = response.genreIds,
            title = response.title,
            voteAverage = response.voteAverage,
            description = response.description,
            releaseDate = response.releaseDate
        )
    }

    override fun mapToResponse(domainModel: Movie): MovieResponse {
        return MovieResponse(
            popularity = domainModel.popularity,
            voteCount = domainModel.voteCount,
            isVideo = domainModel.isVideo,
            posterUrlPath = domainModel.posterUrlPath,
            id = domainModel.id,
            backdropUrlPath = domainModel.backdropUrlPath,
            originalLanguage = domainModel.originalLanguage,
            originalTitle = domainModel.originalTitle,
            genreIds = domainModel.genreIds,
            title = domainModel.title,
            voteAverage = domainModel.voteAverage,
            description = domainModel.description,
            releaseDate = domainModel.releaseDate
        )
    }

    fun responseListToMovieList(responses :  List<MovieResponse>) : List<Movie> {
        val list : ArrayList<Movie> = ArrayList()
        for (response in responses) {
            list.add(mapFromResponse(response))
        }
        return list
    }
}