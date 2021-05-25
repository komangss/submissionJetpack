package com.komangss.submissionjetpack.utils.datagenerator

import com.komangss.submissionjetpack.framework.mapper.CatalogTvShowMapper
import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
import com.komangss.submissionjetpack.framework.network.model.TvShowResultResponse

object TvShowDataGenerator {
    private val tvShowMapper = CatalogTvShowMapper()

    private val tvShowResultResponse = TvShowResultResponse(
        1,
        1,
        1,
        listOf(
            TvShowResponse(
                popularity = 2942.16,
                voteCount = 2853,
                posterUrlPath = "/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
                id = 464052,
                backdropUrlPath = "/srYya1ZlI97Au4jUYAktDe3avyA.jpg",
                originalLanguage = "en",
                originalName = "Wonder Woman 1984",
                genreIds = listOf(14, 28, 12),
                name = "Wonder Woman 1984",
                voteAverage = 7.1,
                description = "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
                releaseDate = "2020-12-16",
                originalCountry = listOf()
            )
        )
    )

    val tvShowDomain = tvShowMapper.responseToDomain(tvShowResultResponse.results!!.first()).apply {
        this.isFavorite = false
    }

    val tvShowEntity = tvShowMapper.domainToEntity(tvShowDomain)

    val tvShowDomainList =
        listOf(
            tvShowDomain
        )

    val tvShowEntityList =
        listOf(
            tvShowEntity
        )
}