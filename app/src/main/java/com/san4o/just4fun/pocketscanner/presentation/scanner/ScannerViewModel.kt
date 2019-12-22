package com.san4o.just4fun.pocketscanner.presentation.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.pocketscanner.domain.CreateBarcodeParams
import com.san4o.just4fun.pocketscanner.domain.ScannnerInteractor
import com.san4o.just4fun.pocketscanner.presentation.result.BarcodeParams
import kotlinx.coroutines.launch
import ru.sportmaster.android.driven.salespoint.presentation.core.single.LiveEvent
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveEvent
import java.util.*

class ScannerViewModel(
    private val interactor: ScannnerInteractor
) : ViewModel() {
    private val _navigateToResult = SingleLiveEvent<BarcodeParams>()
    val navigateToResult: LiveEvent<BarcodeParams> = _navigateToResult

    fun onBarcodeScanned(barcode: ScannedBarcode) {
        viewModelScope.launch {
            val id = interactor.update(
                CreateBarcodeParams(
                    type = barcode.type,
                    date = Date(),
                    name = "",
                    data = barcode.text
                )
            )

            _navigateToResult.call(
                BarcodeParams(
                    id,
                    barcode.bitmap
                )
            )
        }

    }
}

