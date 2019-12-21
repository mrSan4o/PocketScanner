package com.san4o.just4fun.pocketscanner.scan.process

import android.graphics.Bitmap
import com.san4o.just4fun.pocketscanner.scan.BarcodeType
import com.san4o.just4fun.pocketscanner.scan.ScanningState

interface ScanningContract {
    interface Interator {
        fun onBarcodeScanned(barcode: ScannedBarcode)
    }

    interface Observer {
        fun stateChanged(state: ScanningState)
    }
}

data class ScannedBarcode(
    val type: BarcodeType,
    val text: String,
    val bitmap: Bitmap?
)