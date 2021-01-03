//package com.komangss.submissionjetpack.framework.cache.mappers
//
//import com.komangss.submissionjetpack.business.domain.model.Movie
//import com.komangss.submissionjetpack.business.domain.util.EntityMapper
//import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
//
//class CacheMapper : EntityMapper<MovieEntity, Movie> {
//    override fun mapFromEntity(entity: MovieEntity): Movie {
//        return Movie(
//            id = entity.id,
//            title = entity.title,
//            director = entity.director,
//            description = entity.description,
//            imageUrl = entity.image,
//            releaseDate = entity.releaseDate,
//            rating = entity.rating
//        )
//    }
//
//    override fun mapToEntity(domainModel: Movie): MovieEntity {
//        return MovieEntity(
//            id = domainModel.id,
//            title = domainModel.title,
//            director = domainModel.director,
//            description = domainModel.description,
//            image = domainModel.imageUrl,
//            releaseDate = domainModel.releaseDate,
//            rating = domainModel.rating
//        )
//    }
//}