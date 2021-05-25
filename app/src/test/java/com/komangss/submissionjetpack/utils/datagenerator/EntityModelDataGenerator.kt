package com.komangss.submissionjetpack.utils.datagenerator

import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object EntityModelDataGenerator {

    fun dummyTvShowEntities(): Flow<List<TvShowEntity>> = flow {
        emit(provideDummyTvShowEntities())
    }

    fun provideDummyTvShowEntities(): ArrayList<TvShowEntity> {
        val dummyTvShowList: ArrayList<TvShowEntity> = ArrayList()
        dummyTvShowList.add(
            TvShowEntity(
                backdropUrlPath = "/hpU2cHC9tk90hswCFEpf5AtbqoL.jpg",
                releaseDate = "1989-12-16",
                genreIds = "16, 35, 10751, 18",
                id = 456,
                name = "The Simpsons",
                originalCountry = listOf("test").toString(),
                originalLanguage = "en",
                originalName = "The Simpsons",
                description = "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
                popularity = 345.772,
                posterUrlPath = "/2IWouZK4gkgHhJa3oyYuSWfSqbG.jpg",
                voteAverage = 7.8,
                voteCount = 5734,
            )
        )
        return dummyTvShowList
    }


}