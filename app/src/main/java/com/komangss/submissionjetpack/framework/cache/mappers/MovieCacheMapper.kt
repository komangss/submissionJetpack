package com.komangss.submissionjetpack.framework.cache.mappers

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.util.EntityMapper
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity

class MovieCacheMapper : EntityMapper<MovieEntity, Movie> {
    override fun mapFromEntity(entity: MovieEntity): Movie {
        return Movie(
            id = entity.id,
            title = entity.title,
            director = entity.director,
            description = entity.description,
            image = entity.image,
            releaseDate = entity.releaseDate,
            rating = entity.rating
        )
    }

    override fun mapToEntity(domainModel: Movie): MovieEntity {
        return MovieEntity(
            id = domainModel.id,
            title = domainModel.title,
            director = domainModel.director,
            description = domainModel.description,
            image = domainModel.image,
            releaseDate = domainModel.releaseDate,
            rating = domainModel.rating
        )
    }

    fun cacheListToMovieList(oldData :  List<MovieEntity>) : List<Movie> {
        val list : ArrayList<Movie> = ArrayList()
        for (entity in oldData) {
            list.add(mapFromEntity(entity))
        }
        return list
    }
}