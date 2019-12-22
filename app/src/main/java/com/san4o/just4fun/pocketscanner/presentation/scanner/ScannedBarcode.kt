package com.san4o.just4fun.pocketscanner.presentation.scanner

import android.graphics.Bitmap
import com.san4o.just4fun.pocketscanner.domain.BarcodeType
import java.util.*

data class ScannedBarcode(
    val type: BarcodeType,
    val text: String,
    val bitmap: Bitmap?,
    val date: Date = Date()
)