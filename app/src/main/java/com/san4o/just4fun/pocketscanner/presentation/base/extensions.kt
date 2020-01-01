package com.san4o.just4fun.pocketscanner.presentation.base

import com.google.zxing.BarcodeFormat
import com.san4o.just4fun.pocketscanner.domain.BarcodeType
import java.text.SimpleDateFormat
import java.util.*

fun Date.toFullDatetimeFormat(): String {
    return SimpleDateFormat("dd.MM.yyyy HH:mm").format(this)
}

fun BarcodeType.toZxingBarcodeFromat(): BarcodeFormat = when (this) {
    BarcodeType.QRCODE -> BarcodeFormat.QR_CODE
}