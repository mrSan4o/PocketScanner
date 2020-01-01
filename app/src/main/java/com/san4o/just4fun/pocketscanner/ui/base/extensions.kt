package com.san4o.just4fun.pocketscanner.ui.base

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

fun Fragment.share(params: SharedParams) {

    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = params.type
    params.subject?.let {
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, it)
    }

    when (params) {
        is SharedParams.SharedText -> {
            shareIntent.putExtra(Intent.EXTRA_TEXT, params.text)
        }
        is SharedParams.SharedBitmap -> {

            val uri = this.requireContext().saveImage(params.bitmap)
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }


    startActivity(Intent.createChooser(shareIntent, params.title))
}

sealed class SharedParams(
    open val type: String,
    open val title: String,
    open val subject: String?
) {
    data class SharedText(
        override val title: String,
        override val subject: String?,
        val text: String

    ) : SharedParams("text/plain", title, subject)

    data class SharedBitmap(
        override val title: String,
        override val subject: String? = null,
        val bitmap: Bitmap

    ) : SharedParams("text/plain", title, subject)

}

private fun Context.saveImage(image: Bitmap): Uri? { //TODO - Should be processed in another thread
    val imagesFolder = File(getCacheDir(), "images")
    var uri: Uri? = null
    try {
        imagesFolder.mkdirs()
        val file = File(imagesFolder, "shared_image.png")
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 90, stream)
        stream.flush()
        stream.close()
        uri = FileProvider.getUriForFile(this, "com.mydomain.fileprovider", file)
    } catch (e: IOException) {
        Timber.e(e, "IOException while trying to write file for sharing")
    }
    return uri
}

private fun Context.shareImageUri(uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = "image/png"
    startActivity(intent)
}