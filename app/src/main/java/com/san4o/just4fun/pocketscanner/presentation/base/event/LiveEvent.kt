package ru.sportmaster.android.driven.salespoint.presentation.core.single

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface LiveEvent<T> {
    @MainThread
    fun observeForever(observer: Observer<in T>)

    @MainThread
    open fun observe(owner: LifecycleOwner, observer: Observer<in T>)
}