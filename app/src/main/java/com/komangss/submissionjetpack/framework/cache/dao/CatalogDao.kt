package com.komangss.submissionjetpack.framework.cache.dao

import androidx.annotation.WorkerThread
import androidx.room.*
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogDao {
    @WorkerThread
    @Query("SELECT * FROM movie_entity")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>): LongArray

    @WorkerThread
    @Query("SELECT * FROM tv_show_entity")
    fun getTvShows(): Flow<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>): LongArray

    @Query("SELECT * FROM movie_entity WHERE id = :id")
    suspend fun getMovieById(id : Int) : Flow<MovieEntity?>

    @Query("SELECT * FROM tv_show_entity WHERE id = :id")
    suspend fun getTvShowById(id : Int) : Flow<TvShowEntity?>
}