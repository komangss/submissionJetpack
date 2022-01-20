package com.komangss.submissionjetpack.core.di

import android.content.Context
import androidx.room.Room
import com.komangss.submissionjetpack.core.data.source.local.CatalogLocalDataSource
import com.komangss.submissionjetpack.core.data.source.local.room.CatalogDao
import com.komangss.submissionjetpack.core.data.source.local.room.CatalogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCatalogDatabase(@ApplicationContext context: Context): CatalogDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("githubUser".toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            CatalogDatabase::class.java,
            "catalog_database"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCatalogDao(catalogDatabase: CatalogDatabase): CatalogDao = catalogDatabase.catalogDao

    @Singleton
    @Provides
    fun provideCatalogLocalDataSource(catalogDao: CatalogDao): CatalogLocalDataSource {
        return CatalogLocalDataSource(catalogDao)
    }

}