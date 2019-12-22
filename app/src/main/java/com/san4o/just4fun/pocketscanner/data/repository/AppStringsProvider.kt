package com.san4o.just4fun.pocketscanner.data.repository

import android.content.Context

class AppStringsProvider(
    private val context: Context
) : StringProvider {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}