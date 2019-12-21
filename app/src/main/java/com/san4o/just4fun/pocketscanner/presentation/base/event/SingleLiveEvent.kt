package ru.sportmaster.android.driven.salespoint.presentation.core.single


class SingleLiveEvent<T> : AbstractSingleLiveEvent<T>(){


    fun call(t: T) {
        setValue(t)
    }

    fun post(t: T) {
        setValue(t)
    }

    companion object {

        fun longEvent(): SingleLiveEvent<Long> {
            return SingleLiveEvent()
        }
        fun intEvent(): SingleLiveEvent<Int> {
            return SingleLiveEvent()
        }
        fun stringEvent(): SingleLiveEvent<String> {
            return SingleLiveEvent()
        }
    }

}
