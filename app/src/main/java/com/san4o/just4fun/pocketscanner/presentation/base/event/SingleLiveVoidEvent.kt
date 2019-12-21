package ru.sportmaster.android.driven.salespoint.presentation.core.single

class SingleLiveVoidEvent : AbstractSingleLiveEvent<Void>() {

    fun call() {
        setValue(null)
    }

    fun post() {
        postValue(null)
    }
}
