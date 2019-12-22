package com.san4o.just4fun.pocketscanner.data.repository

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.san4o.just4fun.pocketscanner.domain.DeviceManager

class DeviceManagerImpl(
    private val context: Context
) : DeviceManager {

    override fun copyResultInMemory(data: String) {
        val clipboard: ClipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(
            ClipData.newPlainText("scanned barcode", data)
        )
    }
}