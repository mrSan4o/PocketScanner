package com.san4o.just4fun.pocketscanner

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val appModule = module {

    viewModel { MainViewModel() }
}


internal val koinModules: List<Module> = listOf(
    appModule
)
