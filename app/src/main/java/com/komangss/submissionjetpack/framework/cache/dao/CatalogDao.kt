package com.komangss.submissionjetpack.framework.cache.dao

import androidx.annotation.WorkerThread
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @WorkerThread
    @Query("SELECT * FROM movie_entity WHERE id = :id")
    fun getMovieById(id : Int) : Flow<MovieEntity>

    @Query("SELECT * FROM tv_show_entity WHERE id = :id")
    suspend fun getTvShowById(id : Int) : TvShowEntity?

    @WorkerThread
    @Query("SELECT * FROM movie_entity WHERE isFavorite = 1")
    fun getFavoriteMovies() : DataSource.Factory<Int, MovieEntity>

    @WorkerThread
    @Query("SELECT * FROM tv_show_entity WHERE isFavorite = 1")
    fun getFavoriteTvShows() : DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShow: TvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)
}