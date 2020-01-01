package com.san4o.just4fun.pocketscanner.domain

import android.graphics.Bitmap

interface EncodeManager {
    fun encodeAsBitmap(params: EncodeParams): Bitmap?
}