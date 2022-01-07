package com.komangss.submissionjetpack.core.mapper

import com.komangss.submissionjetpack.core.data.source.local.entity.MovieEntity
import com.komangss.submissionjetpack.core.data.source.remote.response.MovieResponse
import com.komangss.submissionjetpack.core.domain.model.Movie
import java.util.regex.Pattern
import javax.inject.Inject

class CatalogMovieMapper @Inject constructor() :
    MapperInterface<Movie, MovieEntity, MovieResponse> {
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
            releaseDate = response.releaseDate ?: "",
            isFavorite = false
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
            releaseDate = entity.releaseDate,
            isFavorite = entity.isFavorite
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

    override fun domainToEntity(domain: Movie): MovieEntity {
        return MovieEntity(
            popularity = domain.popularity,
            voteCount = domain.voteCount,
            isVideo = domain.isVideo,
            posterUrlPath = domain.posterUrlPath,
            id = domain.id,
            backdropUrlPath = domain.backdropUrlPath,
            originalLanguage = domain.originalLanguage,
            originalTitle = domain.originalTitle,
            genreIds = domain.genreIds.toString(),
            title = domain.title,
            voteAverage = domain.voteAverage,
            description = domain.description,
            releaseDate = domain.releaseDate,
            isFavorite = domain.isFavorite
        )
    }

    override fun responseToDomain(response : MovieResponse): Movie {
        return Movie(
            popularity = response.popularity ?: 0.0,
            voteCount = response.voteCount ?: -1,
            isVideo = response.isVideo ?: false,
            posterUrlPath = response.posterUrlPath ?: "",
            id = response.id ?: -1,
            backdropUrlPath = response.backdropUrlPath ?: "",
            originalLanguage = response.originalLanguage ?: "",
            originalTitle = response.originalTitle ?: "",
            genreIds = response.genreIds ?: listOf(),
            title = response.title ?: "",
            voteAverage = response.voteAverage ?: -1.0,
            description = response.description ?: "",
            releaseDate = response.releaseDate ?: "",
            isFavorite = false
        )
    }

    override fun responsesToDomains(responses: List<MovieResponse>): List<Movie> {
        return responses.map { responseToDomain(it) }
    }
}