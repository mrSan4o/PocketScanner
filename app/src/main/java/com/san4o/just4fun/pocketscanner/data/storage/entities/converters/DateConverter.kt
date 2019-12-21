package com.san4o.just4fun.pocketscanner.data.storage.entities.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(time: Long?): Date? = if (time == null) {
        null
    } else {
        Date(time)
    }

    @TypeConverter
    fun toTime(date: Date?): Long? = if (date == null) {
        null
    } else {
        date.time
    }
}