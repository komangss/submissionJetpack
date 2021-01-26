package com.komangss.submissionjetpack.framework.mapper

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.network.model.MovieResponse
import java.util.regex.Pattern

class CatalogMovieMapper : MapperInterface<Movie, MovieEntity, MovieResponse> {
    override fun responseToEntity(response: MovieResponse): MovieEntity {
        return MovieEntity(
            popularity = response.popularity ?: 0.0,
            voteCount = response.voteCount ?: -1,
            isVideo = response.isVideo ?: false,
            posterUrlPath = response.posterUrlPath ?: "",
            id = response.id ?: -1,
            backdropUrlPath = response.backdropUrlPath ?: "",
            originalLanguage = response.originalLanguage ?: "",
            originalTitle = response.originalTitle ?: "",
            genreIds = mapGenreIdsToEntity(response.genreIds),
            title = response.title ?: "",
            voteAverage = response.voteAverage ?: -1.0,
            description = response.description ?: "",
            releaseDate = response.releaseDate ?: ""
        )
    }

    override fun entityToDomain(entity: MovieEntity): Movie {
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

    override fun entitiesToDomains(entities: List<MovieEntity>): List<Movie> {
        return entities.map { entityToDomain(it) }
    }

    private fun mapGenreIdsToEntity(genreId: List<Int>?): String {
        return genreId?.toString() ?: ""
    }


    private fun mapGenreIdsEntityToDomainModel(genreId: String): List<Int>? {
        return if (genreId == "") {
            null
        } else {
            val m = Pattern.compile("\\d+").matcher(genreId)
            val result: ArrayList<Int> = ArrayList()
            while (m.find()) {
                result.add(m.group().toInt())
            }
            result
        }
    }

    override fun responsesToEntities(responses: List<MovieResponse>): List<MovieEntity> {
        return responses.map { responseToEntity(it) }
    }
}