package com.komangss.submissionjetpack.utils.datagenerator

import com.komangss.submissionjetpack.business.domain.model.MovieDetail
import com.komangss.submissionjetpack.business.domain.model.TvShowDetail
import com.komangss.submissionjetpack.framework.network.model.*
import com.komangss.submissionjetpack.framework.network.utils.ApiResponse

object ResponseDataGenerator {
    fun provideDummyMovieApiResponseSuccess() =
        ApiResponse.Success(
            value =
            MovieDetailResponse(
                adult = false,
                backdropPath = "/qAhedRxRYWZAgZ8O8pHIl6QHdD7.jpg",
                belongToCollection = BelongsToCollection(
                    id = 688042,
                    name = "Hobbs & Shaw Collection",
                    posterPath = "/eqWAOmte0PQmG41638SGj80VxQE.jpg",
                    backdropPath = "/2VS6bOVFtyAboDySeCVlFqCDTkT.jpg"
                ),
                budget = 200000000,
                genres = listOf(
                    Genres(id = 28, name = "Action"),
                    Genres(id = 12, name = "Adventure"),
                    Genres(id = 35, name = "Comedy")
                ),
                homepage = "https://www.hobbsandshawmovie.com",
                id = 384018,
                imdbId = "tt6806448",
                originalLanguage = "en",
                description = "Ever since US Diplomatic Security Service Agent Hobbs and lawless outcast Shaw first faced off, they just have swapped smacks and bad words. But when cyber-genetically enhanced anarchist Brixton's ruthless actions threaten the future of humanity, both join forces to defeat him. (A spin-off of “The Fate of the Furious,” focusing on Johnson's Luke Hobbs and Statham's Deckard Shaw.)",
                popularity = 564.394,
                posterPath = "/qRyy2UmjC5ur9bDi3kpNNRCc5nc.jpg",
                productionCompanies = listOf(
                    ProductionCompany(
                        id = 25998,
                        logoPath = null,
                        name = "Chris Morgan Productions",
                        originCountry = "US"
                    ), ProductionCompany(
                        id = 73669,
                        logoPath = "/7tmSGstK3KwgcDIuBYLTAayjit9.png",
                        name = "Seven Bucks Productions",
                        originCountry = "US"
                    ), ProductionCompany(
                        id = 33,
                        logoPath = "/8lvHyhjr8oUKOOy2dKXoALWKdp0.png",
                        name = "Universal Pictures",
                        originCountry = "US"
                    )
                ),
                productionCountries = listOf(
                    ProductionCountry(
                        countryCode = "US",
                        name = "United States of America"
                    )
                ),
                releaseDate = "2019-08-01",
                revenue = 760098996,
                runtime = 137,
                spokenLanguages = listOf(
                    SpokenLanguage(
                        englishName = "English",
                        countryCodeName = "en",
                        name = "English"
                    ),
                    SpokenLanguage(
                        englishName = "Russian",
                        countryCodeName = "ru",
                        name = "Pусский"
                    ),
                    SpokenLanguage(englishName = "Samoan", countryCodeName = "sm", name = "")
                ),
                status = "Released",
                tagLine = null,
                title = "Fast & Furious Presents: Hobbs & Shaw",
                video = false,
                voteAverage = 6.9,
                voteCount = 4544
            )
        )

    fun provideDummyTvShowApiResponseSuccess() =
        ApiResponse.Success(
            value = TvShowDetailResponse(
                backdropPath = "/suopoADq0k8YZr4dQXcU6pToj6s.jpg",
                createdBy = null,
                episodeRunTime = listOf(60),
                firstAirDate = "2011-04-17",
                genres = listOf(),
                homepage = "http://www.hbo.com/game-of-thrones",
                id = 1399,
                inProduction = false,
                languages = listOf("en"),
                lastAirDate = "2019-05-19",
                lastEpisodeToAir = LastEpisodeToAir(
                    airDate = "2019-05-19",
                    episodeNumber = 6,
                    id = 1551830,
                    name = "The Iron Throne",
                    description = "In the aftermath of the devastating attack on King's Landing, Daenerys must face the survivors.",
                    productionCode = "806",
                    seasonNumber = 8,
                    stillPath = "/3x8tJon5jXFa1ziAM93hPKNyW7i.jpg",
                    voteAverage = 4.8,
                    voteCount = 112
                ),
                name = "Game of Thrones",
                nextEpisodeToAir = null,
                networks = null,
                numberOfEpisodes = 73,
                numberOfSeasons = 8,
                originCountry = listOf("US"),
                originalLanguage = "en",
                originalName = "Game of Thrones",
                description = "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                popularity = 419.774,
                posterPath = "/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
                productionCompanies = null,
                productionCountries = null,
                seasons = null,
                spokenLanguages = null,
                status = "Ended",
                tagline = "Winter Is Coming",
                type = "Scripted",
                voteAverage = 8.4,
                voteCount = 12538
            )
        )

    fun tvShowDetailResponseToDomain(response: TvShowDetailResponse): TvShowDetail {
        return TvShowDetail(
            response.backdropPath,
            response.firstAirDate,
            response.genres,
            response.homepage,
            response.id,
            response.name,
            response.networks,
            response.description,
            response.popularity,
            response.posterPath,
            response.spokenLanguages,
            response.status,
            response.voteAverage,
            response.voteCount
        )
    }

    fun movieDetailResponseToDomain(response: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            adult = response.adult,
            posterPath = response.posterPath,
            backdropPath = response.backdropPath,
            genres = response.genres,
            id = response.id,
            originalLanguage = response.originalLanguage,
            description = response.description,
            popularity = response.popularity,
            releaseDate = response.releaseDate,
            status = response.status,
            tagLine = response.tagLine,
            title = response.title,
            video = response.video,
            voteAverage = response.voteAverage,
            voteCount = response.voteCount
        )
    }
}