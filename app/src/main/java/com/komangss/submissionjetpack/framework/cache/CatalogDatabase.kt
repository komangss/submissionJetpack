package com.komangss.submissionjetpack.framework.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.komangss.submissionjetpack.framework.cache.dao.CatalogDao
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 2, exportSchema = false)
abstract class CatalogDatabase : RoomDatabase() {
    abstract val catalogDao: CatalogDao
}