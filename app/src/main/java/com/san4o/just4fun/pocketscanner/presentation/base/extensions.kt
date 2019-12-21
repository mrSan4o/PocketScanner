package com.san4o.just4fun.pocketscanner.presentation.base

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFullDatetimeFormat():String {
    return SimpleDateFormat("dd.MM.yyyy HH:mm").format(this)
}