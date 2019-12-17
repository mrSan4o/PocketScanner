package com.san4o.just4fun.pocketscanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _scannedBarcode = MutableLiveData<ScannedBarcode>()
    val scannedBarcode: LiveData<ScannedBarcode> = _scannedBarcode

    private val _state = MutableLiveData<ScanningState>()
    val state: LiveData<ScanningState> = _state


    fun startScanning(){
        _state.value = ScanningState.SCANNING
    }

    fun onBarcodeScanned(barcode: ScannedBarcode) {
        _scannedBarcode.value = barcode
        _state.value = ScanningState.RESULT
    }
}

enum class ScanningState {
    SCANNING,
    RESULT
}