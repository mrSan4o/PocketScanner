package com.san4o.just4fun.pocketscanner.domain

data class EncodeParams(
    val text: String,
    val width: Int,
    val type: BarcodeType
)