package com.san4o.just4fun.pocketscanner.presentation.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.pocketscanner.domain.CreateBarcodeParams
import com.san4o.just4fun.pocketscanner.domain.ScannnerInteractor
import com.san4o.just4fun.pocketscanner.presentation.result.BarcodeParams
import kotlinx.coroutines.launch
import java.util.*

class ScannerViewModel(
    private val interactor: ScannnerInteractor
) : ViewModel() {

    private val _state = MutableLiveData<ScannerState>()
    val state: LiveData<ScannerState> = _state

    init {
        _state.value = ScannerState.Scanning
    }

    fun resumeScanning() {
        _state.value = ScannerState.Scanning
    }

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

            _state.value = ScannerState.Result(
                BarcodeParams(
                    id,
                    barcode.bitmap
                )
            )
        }

    }
}

sealed class ScannerState {
    object Scanning : ScannerState()
    data class Result(val params: BarcodeParams) : ScannerState()
}

