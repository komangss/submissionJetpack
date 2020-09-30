package com.komangss.submissionjetpack.framework.cache.dao

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity


interface CatalogDao {
    @WorkerThread
    @Query("SELECT * FROM movie_entity")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>): LongArray

    @Delete
    fun deleteMovie(movie: MovieEntity): Int
}