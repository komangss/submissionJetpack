package com.komangss.submissionjetpack.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.komangss.submissionjetpack.core.data.source.local.entity.MovieEntity
import com.komangss.submissionjetpack.core.data.source.local.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 2, exportSchema = false)
abstract class CatalogDatabase : RoomDatabase() {
    abstract val catalogDao: CatalogDao
}