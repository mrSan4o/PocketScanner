package com.san4o.just4fun.pocketscanner.domain

class ScannnerInteractor(
    private val repository: BarcodeRepository
) {

    suspend fun update(barcode: CreateBarcodeParams): Long {
        val find = findBarcode(barcode)
        if (find != null) {
            repository.updateDate(find.id, barcode.date)
        } else {
            repository.create(barcode)
        }
        return findBarcode(barcode)?.id ?: throw RuntimeException("Error find barcode $barcode")
    }

    private suspend fun findBarcode(barcode: CreateBarcodeParams): Barcode? {
        val find = repository.find(
            barcode = barcode.data,
            type = barcode.type
        )
        return find
    }

}