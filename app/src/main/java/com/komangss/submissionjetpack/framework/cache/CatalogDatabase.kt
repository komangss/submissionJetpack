package com.komangss.submissionjetpack.framework.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.komangss.submissionjetpack.framework.cache.dao.CatalogDao
import com.komangss.submissionjetpack.framework.cache.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class CatalogDatabase
private constructor(
    private val context: Context
) : RoomDatabase() {
    abstract val catalogDao: CatalogDao
    companion object {
        private var INSTANCE: CatalogDatabase? = null
        fun getInstance(context: Context): CatalogDatabase {
//            we add a synchronized block
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            CatalogDatabase::class.java,
                            "catalog_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return instance
            }
        }
    }
}