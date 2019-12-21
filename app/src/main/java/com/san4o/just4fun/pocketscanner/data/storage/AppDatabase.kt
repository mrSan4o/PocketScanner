package com.san4o.just4fun.pocketscanner.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.san4o.just4fun.pocketscanner.data.storage.dao.BarcodeDao
import com.san4o.just4fun.pocketscanner.data.storage.entities.BarcodeEntity
import com.san4o.just4fun.pocketscanner.data.storage.entities.converters.BarcodeEntityTypeConverter
import com.san4o.just4fun.pocketscanner.data.storage.entities.converters.DateConverter

@Database(
    entities = [
        BarcodeEntity::class
    ],
    version = 1
)
@TypeConverters(
    BarcodeEntityTypeConverter::class,
    DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun provideBarcodeDao(): BarcodeDao

    companion object {

        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            val database =
                instance
            if (database != null) {
                return database
            }

            val createdDatabase = Room
                .databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pocketscanner.db"
                )
                .build()

            instance = createdDatabase

            return createdDatabase
        }

    }
}