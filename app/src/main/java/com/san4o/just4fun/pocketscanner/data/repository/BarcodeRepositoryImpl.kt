package com.san4o.just4fun.pocketscanner.data.repository

import com.san4o.just4fun.pocketscanner.data.storage.dao.BarcodeDao
import com.san4o.just4fun.pocketscanner.data.storage.entities.BarcodeEntity
import com.san4o.just4fun.pocketscanner.data.storage.entities.BarcodeEntityType
import com.san4o.just4fun.pocketscanner.domain.Barcode
import com.san4o.just4fun.pocketscanner.domain.BarcodeRepository
import com.san4o.just4fun.pocketscanner.domain.BarcodeType
import com.san4o.just4fun.pocketscanner.domain.CreateBarcodeParams
import java.util.*

class BarcodeRepositoryImpl(
    private val barcodeDao: BarcodeDao
) : BarcodeRepository {

    override suspend fun findAll(): List<Barcode> {
        return barcodeDao.findAll().map {
            toBarcode(it)
        }
    }

    override suspend fun create(barcode: CreateBarcodeParams): Long {
        return barcodeDao.insert(
            BarcodeEntity(
                barcode = barcode.data,
                type = barcode.type.toBarcodeEntityType(),
                name = barcode.name,
                date = barcode.date
            )
        )
    }

    override suspend fun find(id: Long): Barcode? {
        return barcodeDao.find(id)
            ?.let { toBarcode(it) }
    }

    override suspend fun find(barcode: String, type: BarcodeType): Barcode? {
        return barcodeDao.find(barcode, type.toBarcodeEntityType())
            ?.let { toBarcode(it) }
    }

    override suspend fun updateBarcodeName(entityId: Long, name: String) {
        barcodeDao.updateName(entityId, name)
    }

    private fun toBarcode(it: BarcodeEntity): Barcode {
        return Barcode(
            id = it.id,
            type = when (it.type) {
                BarcodeEntityType.QRCODE -> BarcodeType.QRCODE
            },
            data = it.barcode,
            name = it.name,
            date = it.date
        )
    }

    override suspend fun updateDate(id: Long, date: Date) {
        barcodeDao.updateDate(id, date)
    }
}

private fun BarcodeType.toBarcodeEntityType(): BarcodeEntityType = when (this) {
    BarcodeType.QRCODE -> BarcodeEntityType.QRCODE
}
