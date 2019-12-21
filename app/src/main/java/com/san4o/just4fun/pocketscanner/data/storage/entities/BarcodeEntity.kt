package com.san4o.just4fun.pocketscanner.data.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "barocode")
data class BarcodeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Long = 0,

    val barcode: String,

    val date: Date,

    val type: BarcodeEntityType,

    val name: String
)

enum class BarcodeEntityType(val id: Int) {
    QRCODE(1)
}