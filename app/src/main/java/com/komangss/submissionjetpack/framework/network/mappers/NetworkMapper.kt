package com.komangss.submissionjetpack.framework.network.mappers

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse

class NetworkMapper {
    fun movieResponseToDomain (response: MovieResponse): Movie {
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

    fun movieResponseListToMovieList(responses :  List<MovieResponse>) : List<Movie> {
        return responses.map { movieResponseToDomain(it) }
    }

     fun tvShowResponseToDomain( response : TvShowResponse) : TvShow {
        return TvShow(
            id = response.id,
            title = response.title,
            description = response.description,
            image = response.image,
            releaseDate = response.releaseDate,
            rating = response.rating
        )
    }

    fun tvShowResponseListToDomain(responses : List<TvShowResponse>) : List<TvShow> {
        return responses.map { tvShowResponseToDomain(it) }
    }
}