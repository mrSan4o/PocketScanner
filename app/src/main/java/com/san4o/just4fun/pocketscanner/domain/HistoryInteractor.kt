package com.san4o.just4fun.pocketscanner.domain

class HistoryInteractor(
    private val repository: BarcodeRepository
) {
    suspend fun findAll(): List<Barcode> {
        return repository.findAll()
    }

    suspend fun removeBarcode(id: Long) {
        repository.removeBarcode(id)
    }
}