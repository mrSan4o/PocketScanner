package com.san4o.just4fun.pocketscanner

import com.san4o.just4fun.pocketscanner.scan.ScanningViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val appModule = module {

    viewModel { ScanningViewModel() }
}


internal val koinModules: List<Module> = listOf(
    appModule
)
