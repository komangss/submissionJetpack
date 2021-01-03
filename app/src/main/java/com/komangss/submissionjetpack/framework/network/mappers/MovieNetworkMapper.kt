package com.komangss.submissionjetpack.framework.network.mappers

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.util.ResponseMapper
import com.komangss.submissionjetpack.framework.network.model.MovieResponse

class MovieNetworkMapper : ResponseMapper<MovieResponse, Movie> {
    override fun mapFromResponse(response: MovieResponse): Movie {
        return Movie(
            id = response.id,
            title = response.title,
            director = response.director,
            description = response.description,
            imageUrl = response.image,
            releaseDate = response.releaseDate,
            rating = response.rating
        )
    }

    override fun mapToResponse(domainModel: Movie): MovieResponse {
        return MovieResponse(
            id = domainModel.id,
            title = domainModel.title,
            director = domainModel.director,
            description = domainModel.description,
            image = domainModel.imageUrl,
            releaseDate = domainModel.releaseDate,
            rating = domainModel.rating
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