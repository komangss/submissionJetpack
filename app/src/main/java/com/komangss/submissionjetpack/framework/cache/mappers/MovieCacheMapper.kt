package com.komangss.submissionjetpack.framework.cache.mappers

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.util.EntityMapper
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity

class MovieCacheMapper : EntityMapper<MovieEntity, Movie> {
    override fun mapFromEntity(entity: MovieEntity): Movie {
        return Movie(
            popularity = entity.popularity,
            voteCount = entity.voteCount,
            isVideo = entity.isVideo,
            posterUrlPath = entity.posterUrlPath,
            id = entity.id,
            backdropUrlPath = entity.backdropUrlPath,
            originalLanguage = entity.originalLanguage,
            originalTitle = entity.originalTitle,
            genreIds = mapGenreIdsEntityToDomainModel(entity.genreIds),
            title = entity.title,
            voteAverage = entity.voteAverage,
            description = entity.description,
            releaseDate = entity.releaseDate
        )
    }

    override fun mapToEntity(domainModel: Movie): MovieEntity {
        return MovieEntity(
            popularity = domainModel.popularity,
            voteCount = domainModel.voteCount,
            isVideo = domainModel.isVideo,
            posterUrlPath = domainModel.posterUrlPath,
            id = domainModel.id,
            backdropUrlPath = domainModel.backdropUrlPath,
            originalLanguage = domainModel.originalLanguage,
            originalTitle = domainModel.originalTitle,
            genreIds = mapGenreIdsToEntity(domainModel.genreIds),
            title = domainModel.title,
            voteAverage = domainModel.voteAverage,
            description = domainModel.description,
            releaseDate = domainModel.releaseDate
        )
    }

    fun cacheListToMovieList(oldData :  List<MovieEntity>) : List<Movie> {
        val list : ArrayList<Movie> = ArrayList()
        for (entity in oldData) {
            list.add(mapFromEntity(entity))
        }
        return list
    }

    private fun mapGenreIdsToEntity(genreIds : List<Int>?) : String {
        return if (genreIds != null) {
            var result = ""
            for ( i in genreIds) {
                if (i == 1) {
                    result += genreIds[i]
                } else {
                    result += ", $genreIds[i]"
                }
            }
            result
        } else {
            ""
        }
    }

    private fun mapGenreIdsEntityToDomainModel (genreIds : String) : List<Int>? {
        return if (genreIds == "") {
            null
        } else {
            val listGenreIds = genreIds.split(", ")
            val result : MutableList<Int>? = mutableListOf()
            for (i in genreIds) {
                val p : Int = listGenreIds[i.toInt()].toInt()
                result?.add(p)
            }
            result
        }
    }
}