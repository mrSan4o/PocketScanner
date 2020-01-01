package com.san4o.just4fun.pocketscanner.presentation.result

import android.graphics.Bitmap
import android.os.Bundle
import androidx.core.os.bundleOf

data class BarcodeParams(
    val id: Long,
    val bitmap: Bitmap? = null
) {
    companion object {
        const val ID = "barcode.id"
        const val BITMAP = "barcode.bitmap"
        fun arguments(params: BarcodeParams): Bundle {
            return bundleOf(
                ID to params.id,
                BITMAP to params.bitmap
            )
        }

        fun getFromArguments(bundle: Bundle?): BarcodeParams {
            return BarcodeParams(
                bundle?.getLong(ID)!!,
                bundle.getParcelable(BITMAP)
            )
        }
    }

}

