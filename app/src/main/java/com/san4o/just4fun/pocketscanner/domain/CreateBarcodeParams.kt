package com.san4o.just4fun.pocketscanner.domain

import java.util.*

data class CreateBarcodeParams(
    val type: BarcodeType,
    val data: String,
    val name: String,
    val date: Date
)