package com.san4o.just4fun.pocketscanner.presentation

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.pocketscanner.domain.Barcode
import com.san4o.just4fun.pocketscanner.domain.BarcodeInteractor
import com.san4o.just4fun.pocketscanner.domain.CreateBarcodeParams
import com.san4o.just4fun.pocketscanner.presentation.base.toFullDatetimeFormat
import kotlinx.coroutines.launch
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveEvent
import timber.log.Timber
import java.util.*

class ScanningViewModel(
    private val interactor: BarcodeInteractor
) : ViewModel(),
    ScannedResultContract.Interactor,
    ScanningContract.Interator {
    val viewState = ResultViewState()

    private val state = MutableLiveData<ScanningState>()

    private val share = SingleLiveEvent<ShareParams>()
    private val open = SingleLiveEvent<OpenParams>()

    init {
        state.observeForever {
            val scanningState = it
            when (scanningState) {
                is ScanningState.RESULT -> {
                    val barcode = scanningState.barcode
                    viewState.date.set(barcode.date.toFullDatetimeFormat())
                    viewState.title.set("")
                    viewState.barcode.set(barcode.data)
                    viewState.bitmap.set(scanningState.bitmap)
                }
            }
        }

        viewModelScope.launch {
            Timber.d("      findAll barcodes:")
            interactor.findAll().forEach {
                Timber.d("      >>> $it")
            }
        }

    }

    override fun onBarcodeScanned(barcode: ScannedBarcode) {
        val date = Date()
        viewModelScope.launch {
            val saved = interactor.update(
                CreateBarcodeParams(
                    type = barcode.type,
                    date = date,
                    name = "",
                    data = barcode.text
                )
            )
            state.postValue(
                ScanningState.RESULT(
                    saved,
                    barcode.bitmap
                )
            )
        }
    }

    override fun onBackToScanning() {
        state.value =
            ScanningState.SCANNING
    }

    override fun onShareResult() {
        onResultState {
            share.call(
                ShareParams(it.barcode.data)
            )
        }
    }

    override fun onOpenResult() {
        onResultState {
            open.call(
                OpenParams(it.barcode.data)
            )
        }
    }

    override fun saveTitleState() {
        val name = viewState.title.get()
        onResultState {
            viewModelScope.launch {
                Timber.d("saveTitleState : %s", name)
                interactor.updateName(it.barcode.id, name ?: "")
            }
        }
    }

    fun observe(owner: LifecycleOwner, observer: ScanningContract.Observer) {
        state.observe(owner, Observer { observer.stateChanged(it) })
    }

    fun observe(owner: LifecycleOwner, observer: ScannedResultContract.Observer) {
        share.observe(owner, Observer { observer.shareBarcodeResult(it) })
        open.observe(owner, Observer { observer.openBarcodeResult(it) })
        state.observe(owner, Observer { observer.stateChanged(it) })
    }

    private inline fun onResultState(func: (ScanningState.RESULT) -> Unit) {
        when (val current = this.state.value) {
            is ScanningState.RESULT -> {
                func.invoke(current)
            }
        }
    }

    fun onBackPressed() {
        when (state.value) {
            is ScanningState.SCANNING -> {
            }
            is ScanningState.RESULT -> state.value = ScanningState.SCANNING
        }

    }
}

sealed class ScanningState {
    object SCANNING : ScanningState()
    data class RESULT(
        val barcode: Barcode,
        val bitmap: Bitmap?
    ) : ScanningState()
}

