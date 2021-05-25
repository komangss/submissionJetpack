package com.komangss.submissionjetpack.utils.datagenerator

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.mapper.CatalogMovieMapper

object MovieDataGenerator {
    val movieMapper = CatalogMovieMapper()

    val movieDomain =
        Movie(
            popularity = 2942.16,
            voteCount = 2853,
            isVideo = false,
            posterUrlPath = "/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
            id = 464052,
            backdropUrlPath = "/srYya1ZlI97Au4jUYAktDe3avyA.jpg",
            originalLanguage = "en",
            originalTitle = "Wonder Woman 1984",
            genreIds = listOf(14, 28, 12),
            title = "Wonder Woman 1984",
            voteAverage = 7.1,
            description = "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
            releaseDate = "2020-12-16",
            isFavorite = false
        )

    val movieEntity = movieMapper.domainToEntity(movieDomain)

    val movieDomainList =
        listOf(
            movieDomain
        )

    val movieEntityList =
        listOf(
            movieEntity
        )
}