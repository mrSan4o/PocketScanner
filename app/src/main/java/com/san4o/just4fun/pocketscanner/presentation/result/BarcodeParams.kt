package com.san4o.just4fun.pocketscanner.presentation.result

import android.graphics.Bitmap

data class BarcodeParams(
    val id: Long,
    val bitmap: Bitmap? = null
)