package com.san4o.just4fun.pocketscanner.ui

import androidx.fragment.app.Fragment
import com.san4o.just4fun.pocketscanner.ui.scanner.ScannerNavigation

interface NavigationListener {
    fun navigate(navItem: ScannerNavigation)
}

fun Fragment.navigate(navItem: ScannerNavigation) {
    val activity = this.requireActivity()
    if (activity is NavigationListener) {
        activity.navigate(navItem)
    }
}