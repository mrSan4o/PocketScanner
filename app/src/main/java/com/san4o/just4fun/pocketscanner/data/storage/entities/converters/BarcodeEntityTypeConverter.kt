package com.san4o.just4fun.pocketscanner.data.storage.entities.converters

import androidx.room.TypeConverter
import com.san4o.just4fun.pocketscanner.data.storage.entities.BarcodeEntityType

class BarcodeEntityTypeConverter {
    @TypeConverter
    fun toModel(id: Int?): BarcodeEntityType? {
        if (id == null || id == 0) {
            return null
        }
        for (identType in BarcodeEntityType.values()) {
            if (identType.id == id) {
                return identType
            }
        }
        throw RuntimeException("Error id=$id for ClubproIdentType")
    }

    @TypeConverter
    fun fromModel(type: BarcodeEntityType?): Int? {
        return type?.id
    }
}