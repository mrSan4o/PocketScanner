package com.san4o.just4fun.pocketscanner.ui.base

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputEditText

object AppBindingAdapters {
    @JvmStatic
    @BindingAdapter("app:bind_text")
    fun setTextViewText(view: TextView, text: String?) {
        view.text = text ?: ""
    }
    @JvmStatic
    @BindingAdapter("app:bind_bitmap")
    fun setImageViewBitmap(view: ImageView, bitmap: Bitmap?) {
        bitmap?.let { view.setImageBitmap(it) }

    }
    @JvmStatic
    @BindingAdapter("app:bind_text")
    fun setEditText(view: TextInputEditText, text: String?) {
        if (view.text?.toString() != text) {
            view.setText(text ?: "")
        }
    }
    @JvmStatic
    @InverseBindingAdapter(attribute = "app:bind_text")
    fun getEditText(view: TextInputEditText): String {
        return view.text?.toString() ?: ""
    }
    @JvmStatic
    @BindingAdapter("app:bind_textAttrChanged")
    fun setEditTextListener(view: TextInputEditText, listener: InverseBindingListener) {
        view.addTextChangedListener(
            afterTextChanged = { listener.onChange() }
        )
    }
}