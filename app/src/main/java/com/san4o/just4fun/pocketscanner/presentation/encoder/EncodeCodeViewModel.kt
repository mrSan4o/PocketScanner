package com.san4o.just4fun.pocketscanner.presentation.encoder

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.WriterException
import com.san4o.just4fun.pocketscanner.domain.BarcodeType
import com.san4o.just4fun.pocketscanner.domain.EncodeManager
import com.san4o.just4fun.pocketscanner.domain.EncodeParams
import com.san4o.just4fun.pocketscanner.ui.base.SharedParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sportmaster.android.driven.salespoint.presentation.core.single.LiveEvent
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveEvent

class EncodeCodeViewModel(
    private val encodeManager: EncodeManager
) : ViewModel() {
    private val _bitmap = MutableLiveData<Bitmap>()
    val bitmap: LiveData<Bitmap> = _bitmap

    private val _share = SingleLiveEvent<SharedParams.SharedBitmap>()
    val share: LiveEvent<SharedParams.SharedBitmap> = _share

    fun onShareResult() {

        _bitmap.value?.let {
            _share.call(
                SharedParams.SharedBitmap(
                    title = "Отправить штрихкод",
                    bitmap = it
                )
            )
        }
    }

    fun onTextEntered(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _bitmap.postValue(
                encodeAsBitmap(
                    EncodeParams(
                        text = text,
                        width = 500,
                        type = BarcodeType.QRCODE
                    )
                )
            )
        }
    }

    @Throws(WriterException::class)
    private fun encodeAsBitmap(params: EncodeParams): Bitmap? {
        return encodeManager.encodeAsBitmap(params)
    }
}

