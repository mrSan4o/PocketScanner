package com.san4o.just4fun.pocketscanner.domain

class BarcodeInteractor(
    private val repository: BarcodeRepository
) {

    suspend fun update(barcode: CreateBarcodeParams): Barcode {
        val find = findBarcode(barcode)
        if (find != null) {
            repository.updateDate(find.id, barcode.date)
        } else {
            repository.create(barcode)
        }
        return findBarcode(barcode) ?: throw RuntimeException("Error find barcode $barcode")
    }

    suspend fun updateName(id: Long, name: String) {
        repository.updateBarcodeName(id, name)
    }

    private suspend fun findBarcode(barcode: CreateBarcodeParams): Barcode? {
        val find = repository.find(
            barcode = barcode.data,
            type = barcode.type
        )
        return find
    }

    suspend fun findAll(): List<Barcode> {
        return repository.findAll()
    }
}