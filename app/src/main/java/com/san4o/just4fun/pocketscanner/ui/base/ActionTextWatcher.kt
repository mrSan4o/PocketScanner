package com.san4o.just4fun.pocketscanner.ui.base

import android.text.Editable
import android.text.TextWatcher
import java.util.*

class ActionTextWatcher(private val action: () -> Unit) :
    TextWatcher {
    val delay: Long = 1000
    var startAtTime: Long = 0
    var timer = Timer()
    override fun afterTextChanged(s: Editable?) {
        if (System.currentTimeMillis() - startAtTime > delay) {
            timer.cancel()
            timer = Timer()
        }

        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    action.invoke()
                }
            },
            delay
        )
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}