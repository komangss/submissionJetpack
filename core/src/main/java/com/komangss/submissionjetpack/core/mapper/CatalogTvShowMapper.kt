package com.komangss.submissionjetpack.core.mapper

import com.komangss.submissionjetpack.core.data.source.local.entity.TvShowEntity
import com.komangss.submissionjetpack.core.data.source.remote.response.TvShowResponse
import com.komangss.submissionjetpack.core.domain.model.TvShow
import java.util.regex.Pattern
import javax.inject.Inject

class CatalogTvShowMapper @Inject constructor() :
    MapperInterface<TvShow, TvShowEntity, TvShowResponse> {
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
            originalCountry = mapOriginalCountriesEntityToDomainModel(entity.originalCountry),
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

    override fun domainToEntity(domain: TvShow): TvShowEntity {
        return TvShowEntity(
            backdropUrlPath = domain.backdropUrlPath,
            releaseDate = domain.releaseDate,
            genreIds = domain.genreIds.toString(),
            id = domain.id,
            name = domain.name,
            originalCountry = mapOriginalCountriesToEntity(domain.originalCountry),
            originalLanguage = domain.originalLanguage,
            originalName = domain.originalName,
            description = domain.description,
            popularity = domain.popularity,
            posterUrlPath = domain.posterUrlPath,
            voteAverage = domain.voteAverage,
            voteCount = domain.voteCount,
            isFavorite = domain.isFavorite
        )
    }

    override fun responseToDomain(response: TvShowResponse): TvShow {
        return TvShow(
            backdropUrlPath = response.backdropUrlPath ?: "",
            releaseDate = response.releaseDate ?: "",
            genreIds = response.genreIds ?: listOf(),
            id = response.id ?: -1,
            name = response.name ?: "",
            originalCountry = response.originalCountry ?: listOf(),
            originalLanguage = response.originalLanguage ?: "",
            originalName = response.originalName ?: "",
            description = response.description ?: "",
            popularity = response.popularity ?: -1.0,
            posterUrlPath = response.posterUrlPath ?: "",
            voteAverage = response.voteAverage ?: -1.0,
            voteCount = response.voteCount ?: -1,
            isFavorite = false
        )
    }

    override fun responsesToDomains(responses: List<TvShowResponse>): List<TvShow> {
        return responses.map { responseToDomain(it) }
    }

    private fun mapOriginalCountriesToEntity(originalCountries: List<String>?): String {
        return originalCountries?.toString() ?: ""
    }

    private fun mapOriginalCountriesEntityToDomainModel(originalCountries: String): List<String>? {
        return if (originalCountries == "" || originalCountries == "[]") {
            null
        } else {
            var originalCountriesResult = originalCountries.replace(" ", "")
            originalCountriesResult =
                originalCountries.substring(1, originalCountriesResult.length - 1)
            val result: List<String> = originalCountriesResult.split(",")
            result
        }
    }
}