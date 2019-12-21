package ru.sportmaster.android.driven.salespoint.presentation.core.single

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

abstract class AbstractSingleLiveEvent<T> : LiveData<T>(), LiveEvent<T> {
    private val mPending = AtomicBoolean(false)



    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        if (super.hasActiveObservers()) {
            Log.w(
                javaClass.simpleName,
                "Multiple observers registered but only one will be notified of changes."
            )
        }

        // Observe the internal MutableLiveData
        super.observeForever({ t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (super.hasActiveObservers()) {
            Log.w(
                javaClass.simpleName,
                "Multiple observers registered but only one will be notified of changes."
            )
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }




    fun clear() {
        mPending.set(false)
    }

    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }

    override fun postValue(value: T?) {
        mPending.set(true)
        super.postValue(value)
    }



}
