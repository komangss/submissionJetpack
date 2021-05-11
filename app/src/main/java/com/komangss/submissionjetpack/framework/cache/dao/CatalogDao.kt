package com.komangss.submissionjetpack.framework.cache.dao

import androidx.annotation.WorkerThread
import androidx.paging.DataSource
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

    @WorkerThread
    @Query("SELECT * FROM movie_entity WHERE id = :id")
    fun getMovieById(id : Int) : Flow<MovieEntity>

    @WorkerThread
    @Query("SELECT * FROM tv_show_entity WHERE id = :id")
    fun getTvShowById(id : Int) : Flow<TvShowEntity>

    @WorkerThread
    @Query("SELECT * FROM movie_entity WHERE isFavorite = 1")
    fun getFavoriteMovies() : DataSource.Factory<Int, MovieEntity>

    @WorkerThread
    @Query("SELECT * FROM tv_show_entity WHERE isFavorite = 1")
    fun getFavoriteTvShows() : DataSource.Factory<Int, TvShowEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTvShow(tvShow: TvShowEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(movie: MovieEntity)
}