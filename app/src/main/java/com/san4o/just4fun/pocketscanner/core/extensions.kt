package com.san4o.just4fun.pocketscanner.core

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFullDatetimeFormat():String {
    return SimpleDateFormat("dd.MM.yyyy HH:mm").format(this)
}