package com.san4o.just4fun.pocketscanner.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.san4o.just4fun.pocketscanner.data.storage.dao.BarcodeDao
import com.san4o.just4fun.pocketscanner.data.storage.entities.BarcodeEntity
import com.san4o.just4fun.pocketscanner.data.storage.entities.BarcodeEntityType
import com.san4o.just4fun.pocketscanner.data.storage.entities.converters.BarcodeEntityTypeConverter
import com.san4o.just4fun.pocketscanner.data.storage.entities.converters.DateConverter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

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

            GlobalScope.launch {
                val dao = createdDatabase.provideBarcodeDao()
                val entity = dao.find(1L)
                if (entity == null) {
                    dao.insert(
                        BarcodeEntity(
                            id = 1L,
                            barcode = "1231231",
                            date = Date(),
                            type = BarcodeEntityType.QRCODE,
                            name = "zzz"
                        )
                    )
                }
            }

            instance = createdDatabase

            return createdDatabase
        }

    }
}