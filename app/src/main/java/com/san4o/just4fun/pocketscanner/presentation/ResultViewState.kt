package com.san4o.just4fun.pocketscanner.presentation

import android.graphics.Bitmap
import androidx.databinding.ObservableField

class ResultViewState :
    ScannedResultContract.ViewState {
    override val date = ObservableField<String>()
    override val title = ObservableField<String>()
    override val barcode = ObservableField<String>()
    override val bitmap = ObservableField<Bitmap>()
}