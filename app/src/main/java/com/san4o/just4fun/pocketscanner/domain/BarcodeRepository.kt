package com.san4o.just4fun.pocketscanner.domain

import java.util.*

interface BarcodeRepository {
    suspend fun findAll(): List<Barcode>
    suspend fun find(id: Long): Barcode?
    suspend fun find(barcode: String, type: BarcodeType): Barcode?
    suspend fun create(barcode: CreateBarcodeParams): Long
    suspend fun updateBarcodeName(entityId: Long, name: String)
    suspend fun updateDate(id: Long, date: Date)
    suspend fun removeBarcode(id: Long)
}