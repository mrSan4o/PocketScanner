package com.san4o.just4fun.pocketscanner

import android.graphics.Bitmap

data class ScannedBarcode(
    val type: BarcodeType,
    val text: String,
    val bitmap: Bitmap
) {
}