package com.san4o.just4fun.pocketscanner.presentation

import android.graphics.Bitmap
import androidx.databinding.ObservableField

interface ScannedResultContract {
    interface ViewState {
        val date: ObservableField<String>
        val title: ObservableField<String>
        val barcode: ObservableField<String>
        val bitmap: ObservableField<Bitmap>
    }

    interface Interactor {
        fun onBackToScanning()
        fun onShareResult()
        fun onOpenResult()
        fun saveTitleState()
    }

    interface Observer {
        fun stateChanged(state: ScanningState)
        fun shareBarcodeResult(data: ShareParams)
        fun openBarcodeResult(data: OpenParams)
    }
}

data class ShareParams(val url: String)
data class OpenParams(val url: String)