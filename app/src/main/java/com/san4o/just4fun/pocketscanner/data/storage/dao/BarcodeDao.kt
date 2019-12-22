package com.san4o.just4fun.pocketscanner.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.san4o.just4fun.pocketscanner.data.storage.entities.BarcodeEntity
import com.san4o.just4fun.pocketscanner.data.storage.entities.BarcodeEntityType
import java.util.*

@Dao
interface BarcodeDao {

    @Query("select * from barocode")
    suspend fun findAll(): List<BarcodeEntity>

    @Insert
    suspend fun insert(entity: BarcodeEntity): Long

    @Query("UPDATE barocode set name = :name where id = :id")
    suspend fun updateName(id: Long, name: String)

    @Query("select * from barocode where barcode = :barcode and type = :type")
    suspend fun find(barcode: String, type: BarcodeEntityType): BarcodeEntity?

    @Query("UPDATE barocode set date = :date where id = :id")
    suspend fun updateDate(id: Long, date: Date)

    @Query("select * from barocode where id = :id")
    suspend fun find(id: Long): BarcodeEntity?
}