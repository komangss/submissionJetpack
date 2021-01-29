package com.komangss.submissionjetpack.framework.mapper

import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
import java.util.regex.Pattern

class CatalogTvShowMapper : MapperInterface<TvShow, TvShowEntity, TvShowResponse> {
    override fun responseToEntity(response: TvShowResponse): TvShowEntity {
        return TvShowEntity(
            backdropUrlPath = response.backdropUrlPath ?: "",
            releaseDate = response.releaseDate ?: "",
            genreIds = mapGenreIdsToEntity(response.genreIds),
            id = response.id ?: -1,
            name = response.name ?: "",
            originalCountry = response.originalCountry.toString(),
            originalLanguage = response.originalLanguage ?: "",
            originalName = response.originalName ?: "",
            description = response.description ?: "",
            popularity = response.popularity ?: -1.0,
            posterUrlPath = response.posterUrlPath ?: "",
            voteAverage = response.voteAverage ?: -1.0,
            voteCount = response.voteCount ?: -1
        )
    }

    override fun entityToDomain(entity: TvShowEntity): TvShow {
        return TvShow(
            backdropUrlPath = entity.backdropUrlPath,
            releaseDate = entity.releaseDate,
            genreIds = mapGenreIdsEntityToDomainModel(entity.genreIds),
            id = entity.id,
            name = entity.name,

//            TODO : string to list of string
            originalCountry = listOf("en"),

            originalLanguage = entity.originalLanguage,
            originalName = entity.originalName,
            description = entity.description,
            popularity = entity.popularity,
            posterUrlPath = entity.posterUrlPath,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount,
            isFavorite = entity.isFavorite
        )
    }

    override fun entitiesToDomains(entities: List<TvShowEntity>): List<TvShow> {
        return entities.map { entityToDomain(it) }
    }

    override fun responsesToEntities(responses: List<TvShowResponse>): List<TvShowEntity> {
        return responses.map { responseToEntity(it) }
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

    override fun domainToEntity(d: TvShow): TvShowEntity {
        return TvShowEntity(
            backdropUrlPath = d.backdropUrlPath,
            releaseDate = d.releaseDate,
            genreIds = d.genreIds.toString(),
            id = d.id,
            name = d.name,
            originalCountry = listOf("en").toString(),
            originalLanguage = d.originalLanguage,
            originalName = d.originalName,
            description = d.description,
            popularity = d.popularity,
            posterUrlPath = d.posterUrlPath,
            voteAverage = d.voteAverage,
            voteCount = d.voteCount,
            isFavorite = d.isFavorite
        )
    }
}