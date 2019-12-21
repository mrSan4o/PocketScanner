package com.san4o.just4fun.pocketscanner

import com.san4o.just4fun.pocketscanner.data.repository.BarcodeRepositoryImpl
import com.san4o.just4fun.pocketscanner.data.storage.AppDatabase
import com.san4o.just4fun.pocketscanner.domain.BarcodeInteractor
import com.san4o.just4fun.pocketscanner.domain.BarcodeRepository
import com.san4o.just4fun.pocketscanner.presentation.ScanningViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val appModule = module {

    factory<BarcodeRepository> { BarcodeRepositoryImpl(get()) }
    factory { BarcodeInteractor(get()) }

    viewModel { ScanningViewModel(get()) }
}

private val databaseModule = module {
    single { AppDatabase.get(get()) }
    single { get<AppDatabase>().provideBarcodeDao() }
}


internal val koinModules: List<Module> = listOf(
    appModule,
    databaseModule
)
