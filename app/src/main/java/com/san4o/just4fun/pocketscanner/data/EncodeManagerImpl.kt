package com.san4o.just4fun.pocketscanner.data

import android.graphics.Bitmap
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.san4o.just4fun.pocketscanner.domain.EncodeManager
import com.san4o.just4fun.pocketscanner.domain.EncodeParams
import com.san4o.just4fun.pocketscanner.presentation.base.toZxingBarcodeFromat

class EncodeManagerImpl : EncodeManager {

    private val WHITE = -0x1
    private val BLACK = -0x1000000

    override fun encodeAsBitmap(params: EncodeParams): Bitmap? {
        val text = params.text
        val width = params.width
        val type = params.type

        val result: BitMatrix = try {
            MultiFormatWriter().encode(
                text,
                type.toZxingBarcodeFromat(), width, width, null
            )
        } catch (iae: IllegalArgumentException) { // Unsupported format
            return null
        }
        val w = result.width
        val h = result.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                pixels[offset + x] = if (result[x, y]) BLACK else WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h)
        return bitmap
    }

}