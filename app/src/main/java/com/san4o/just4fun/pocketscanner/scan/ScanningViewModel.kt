package com.san4o.just4fun.pocketscanner.scan

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.san4o.just4fun.pocketscanner.core.toFullDatetimeFormat
import com.san4o.just4fun.pocketscanner.scan.process.ScannedBarcode
import com.san4o.just4fun.pocketscanner.scan.process.ScanningContract
import com.san4o.just4fun.pocketscanner.scan.result.OpenParams
import com.san4o.just4fun.pocketscanner.scan.result.ResultViewState
import com.san4o.just4fun.pocketscanner.scan.result.ScannedResultContract
import com.san4o.just4fun.pocketscanner.scan.result.ShareParams
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveEvent
import timber.log.Timber
import java.util.*

class ScanningViewModel : ViewModel(),
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
                    val barcode = scanningState.data
                    viewState.date.set(scanningState.date.toFullDatetimeFormat())
                    viewState.title.set("")
                    viewState.barcode.set(barcode.text)
                    viewState.bitmap.set(barcode.bitmap)
                }
            }
        }

    }

    override fun onBarcodeScanned(barcode: ScannedBarcode) {
        state.postValue(
            ScanningState.RESULT(
                barcode,
                Date()
            )
        )
    }

    override fun onBackToScanning() {
        state.value =
            ScanningState.SCANNING
    }

    override fun onShareResult() {
        onResultState {
            share.call(
                ShareParams(it.data.text)
            )
        }
    }

    override fun onOpenResult() {
        onResultState {
            open.call(
                OpenParams(it.data.text)
            )
        }
    }

    override fun saveTitleState() {
        Timber.d("saveTitleState : %s", viewState.title.get())
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
}

sealed class ScanningState {
    object SCANNING : ScanningState()
    data class RESULT(
        val data: ScannedBarcode,
        val date: Date
    ) : ScanningState()
}

