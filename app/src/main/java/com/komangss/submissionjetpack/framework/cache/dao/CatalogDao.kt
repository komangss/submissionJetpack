package com.komangss.submissionjetpack.framework.cache.dao

import androidx.annotation.WorkerThread
import androidx.room.*
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogDao {
    @WorkerThread
    @Query("SELECT * FROM movie_entity")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>): LongArray

}