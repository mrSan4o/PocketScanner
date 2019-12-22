package com.san4o.just4fun.pocketscanner

import com.san4o.just4fun.pocketscanner.data.repository.AppStringsProvider
import com.san4o.just4fun.pocketscanner.data.repository.BarcodeRepositoryImpl
import com.san4o.just4fun.pocketscanner.data.repository.DeviceManagerImpl
import com.san4o.just4fun.pocketscanner.data.repository.StringProvider
import com.san4o.just4fun.pocketscanner.data.storage.AppDatabase
import com.san4o.just4fun.pocketscanner.domain.*
import com.san4o.just4fun.pocketscanner.presentation.history.HistoryViewModel
import com.san4o.just4fun.pocketscanner.presentation.result.BarcodeParams
import com.san4o.just4fun.pocketscanner.presentation.result.ScanningResultViewModel
import com.san4o.just4fun.pocketscanner.presentation.scanner.ScannerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val appModule = module {

    factory<StringProvider> { AppStringsProvider(get()) }
    factory<DeviceManager> { DeviceManagerImpl(get()) }
    factory<BarcodeRepository> { BarcodeRepositoryImpl(get()) }
    factory {
        BarcodeInteractor(
            deviceManager = get(),
            repository = get()
        )
    }
    factory {
        HistoryInteractor(
            repository = get()
        )
    }
    factory {
        ScannnerInteractor(
            repository = get()
        )
    }

    viewModel { (barcode: BarcodeParams) ->
        ScanningResultViewModel(
            interactor = get(),
            stringProvider = get(),
            params = barcode
        )
    }

    viewModel { HistoryViewModel(get()) }
    viewModel { ScannerViewModel(get()) }
}

private val databaseModule = module {
    single { AppDatabase.get(get()) }
    single { get<AppDatabase>().provideBarcodeDao() }
}

internal val koinModules: List<Module> = listOf(
    appModule,
    databaseModule
)
