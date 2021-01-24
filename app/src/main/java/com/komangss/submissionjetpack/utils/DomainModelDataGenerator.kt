package com.komangss.submissionjetpack.utils

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.business.domain.model.MovieDetail
import com.komangss.submissionjetpack.business.domain.model.TvShow
import com.komangss.submissionjetpack.business.domain.model.TvShowDetail
import com.komangss.submissionjetpack.framework.network.model.Genres
import com.komangss.submissionjetpack.framework.network.model.Network
import com.komangss.submissionjetpack.framework.network.model.SpokenLanguage

object DomainModelDataGenerator {
    fun generateDummyMovies(): List<Movie> {
        val result = ArrayList<Movie>()
        result.add(
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
                releaseDate = "2020-12-16"
            )

        )
        return result
    }

    fun getMovieById(): MovieDetail =
        MovieDetail(
            adult = false,
            posterPath = "/vVYU0x9FRpiJNX7c54ciFnRBVYG.jpg",
            backdropPath = "/8knaRrDd1FM1pbSLaViEQSxodi5.jpg",
            genres = listOf(
                Genres(id = 18, name = "Drama"),
                Genres(id = 53, name = "Thriller")
            ),
            id = 441282,
            originalLanguage = "en",
            description = "A Minnesota police officer crosses paths with a committed and tireless vigilante as he follows the trail of a ruthless predator responsible for several abductions and murders.",
            popularity = 182.865,
            releaseDate = "2019-08-29",
            status = "Released",
            tagLine = null,
            title = "Night Hunter",
            video = false,
            voteAverage = 6.5,
            voteCount = 432
        )

    fun generateDummyTvShows(): List<TvShow> {
        val result = ArrayList<TvShow>()
        result.add(
            TvShow(
                backdropUrlPath = "/hpU2cHC9tk90hswCFEpf5AtbqoL.jpg",
                releaseDate = "1989-12-16",
                genreIds = listOf(16, 35, 10751, 18),
                id = 456,
                name = "The Simpsons",
                originalCountry = listOf("test"),
                originalLanguage = "en",
                originalName = "The Simpsons",
                description = "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
                popularity = 345.772,
                posterUrlPath = "/2IWouZK4gkgHhJa3oyYuSWfSqbG.jpg",
                voteAverage = 7.8,
                voteCount = 5734
            )
        )
        return result
    }

    fun getTvShowById(): TvShowDetail =
        TvShowDetail(
            backdropPath = "/hpU2cHC9tk90hswCFEpf5AtbqoL.jpg",
            firstAirDate = "1989-12-16",
            genres = listOf(
                Genres(id = 16, name = "Animation"),
                Genres(id = 35, name = "Comedy"),
                Genres(id = 10751, name = "Family"),
                Genres(id = 18, name = "Drama")
            ),
            homepage = "http://www.thesimpsons.com/",
            id = 456,
            name = "The Simpsons",
            networks = listOf(
                Network(
                    name = "FOX",
                    id = 19,
                    logoPath = "/1DSpHrWyOORkL9N2QHX7Adt31mQ.png",
                    originCountry = "US"
                )
            ),
            description = "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
            popularity = 339.336,
            posterPath = "/2IWouZK4gkgHhJa3oyYuSWfSqbG.jpg",
            spokenLanguages = listOf(
                SpokenLanguage(englishName = "English", countryCodeName = "en", name = "English")
            ),
            status = "Returning Series",
            voteAverage = 7.8,
            voteCount = 5827
        )

}

