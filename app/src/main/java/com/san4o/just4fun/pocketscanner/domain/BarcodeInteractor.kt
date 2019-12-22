package com.san4o.just4fun.pocketscanner.domain

class BarcodeInteractor(
    private val repository: BarcodeRepository,
    private val deviceManager: DeviceManager
) {

    suspend fun find(id: Long): Barcode {
        return repository.find(id)!!
    }

    suspend fun updateName(id: Long, name: String) {
        repository.updateBarcodeName(id, name)
    }

    fun copyResultInMemory(data: String) {
        deviceManager.copyResultInMemory(data)
    }
}