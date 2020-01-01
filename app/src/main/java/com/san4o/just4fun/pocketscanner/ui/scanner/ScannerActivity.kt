package com.san4o.just4fun.pocketscanner.ui.scanner

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.san4o.just4fun.pocketscanner.R
import com.san4o.just4fun.pocketscanner.presentation.result.BarcodeParams
import com.san4o.just4fun.pocketscanner.presentation.scanner.ScannerState
import com.san4o.just4fun.pocketscanner.presentation.scanner.ScannerViewModel
import com.san4o.just4fun.pocketscanner.ui.NavigationListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScannerActivity : AppCompatActivity(),
    NavigationListener {

    private val viewModel: ScannerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        setContentView(R.layout.activity_scanner)

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is ScannerState.Scanning -> navigate(ScannerNavigation.Scanner)
                is ScannerState.Result -> navigate(ScannerNavigation.Result(state.params))
            }

        })
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(ResultTag)
        if (fragment != null) {
            navigate(ScannerNavigation.Scanner)
        } else {
            finish()
        }
    }

    override fun navigate(navItem: ScannerNavigation) {
        val fragment = navItem.fragmentClass.newInstance()
        navItem.arguments?.let {
            fragment.arguments = it
        }
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, fragment, navItem.tag)
            .commitNow()

    }
}

private val ScannerTag = "Scanner"
private val ResultTag = "Result"

sealed class ScannerNavigation(
    val fragmentClass: Class<out Fragment>,
    val tag: String,
    val arguments: Bundle? = null
) {

    object Scanner : ScannerNavigation(
        ScannerFragment::class.java,
        ScannerTag
    )

    data class Result(private val params: BarcodeParams) :
        ScannerNavigation(
            ResultFragment::class.java,
            ResultTag,
            BarcodeParams.arguments(
                params
            )
        )
}