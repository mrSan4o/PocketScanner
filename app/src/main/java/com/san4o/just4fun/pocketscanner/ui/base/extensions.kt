package com.san4o.just4fun.pocketscanner.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun <T : ViewDataBinding?> ViewGroup.inflateBinding(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): T {
    return DataBindingUtil.inflate<T>(LayoutInflater.from(context), layoutRes, this, attachToRoot)
}

fun Fragment.toastLong(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_LONG)
        .show()
}

fun View.setVisibile(visible: Boolean) {
    this.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}