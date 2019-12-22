package com.san4o.just4fun.pocketscanner.presentation.result

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.pocketscanner.R
import com.san4o.just4fun.pocketscanner.data.repository.StringProvider
import com.san4o.just4fun.pocketscanner.domain.BarcodeInteractor
import com.san4o.just4fun.pocketscanner.presentation.base.toFullDatetimeFormat
import kotlinx.coroutines.launch
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveEvent
import timber.log.Timber

class ScanningResultViewModel(
    private val interactor: BarcodeInteractor,
    private val stringProvider: StringProvider,
    private val params: BarcodeParams
) : ViewModel(),
    ScannedResultContract.Interactor {

    val viewState =
        ResultViewState()

    private val notifyMessageEvent = SingleLiveEvent<String>()
    private val share = SingleLiveEvent<ShareParams>()
    private val open = SingleLiveEvent<OpenParams>()

    private val barcodeId: Long = params.id
    private lateinit var barcodeData: String

    init {
        viewState.bitmap.set(this.params.bitmap)

        viewModelScope.launch {
            val barcode = interactor.find(barcodeId)
            barcodeData = barcode.data

            viewState.date.set(barcode.date.toFullDatetimeFormat())
            viewState.title.set(barcode.name)
            viewState.barcode.set(barcode.data)
        }
    }

    override fun onShareResult() {
        share.call(
            ShareParams(barcodeData)
        )
    }

    override fun onOpenResult() {
        open.call(
            OpenParams(barcodeData)
        )
    }

    override fun onCopyResultInMemory() {

        interactor.copyResultInMemory(barcodeData)

        notifyMessageEvent.call(
            stringProvider.getString(R.string.barcode_copied_to_clipboard)
        )
    }

    override fun saveTitleState() {
        val name = viewState.title.get()
        viewModelScope.launch {
            Timber.d("saveTitleState : %s", name)

            interactor.updateName(barcodeId, name ?: "")

        }
    }

    fun observe(owner: LifecycleOwner, observer: ScannedResultContract.Observer) {
        share.observe(owner, Observer { observer.shareBarcodeResult(it) })
        open.observe(owner, Observer { observer.openBarcodeResult(it) })
        notifyMessageEvent.observe(owner, Observer { observer.notifyMessage(it) })
    }

}