package com.san4o.just4fun.pocketscanner.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.pocketscanner.domain.HistoryInteractor
import kotlinx.coroutines.launch
import java.util.*

class HistoryViewModel(
    private val interactor: HistoryInteractor
) : ViewModel() {

    private val _historyItems = MutableLiveData<List<HistoryItem>>()
    val historyItems: LiveData<List<HistoryItem>> = _historyItems

    fun loadItems() {
        viewModelScope.launch {
            loadBarcodes()
        }
    }

    fun removeBarcode(id: Long) {
        viewModelScope.launch {
            interactor.removeBarcode(id)

            loadBarcodes()
        }
    }

    private suspend fun loadBarcodes() {
        val items = interactor.findAll()

        _historyItems.value = items.map {
            HistoryItem(
                id = it.id,
                date = it.date,
                barcode = it.data,
                name = it.name
            )
        }
    }
}

data class HistoryItem(
    val id: Long,
    val date: Date,
    val barcode: String,
    val name: String
)