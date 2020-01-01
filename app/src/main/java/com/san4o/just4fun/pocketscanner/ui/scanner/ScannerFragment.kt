package com.san4o.just4fun.pocketscanner.ui.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.san4o.just4fun.pocketscanner.R
import com.san4o.just4fun.pocketscanner.domain.BarcodeType
import com.san4o.just4fun.pocketscanner.presentation.scanner.ScannedBarcode
import com.san4o.just4fun.pocketscanner.presentation.scanner.ScannerViewModel
import com.san4o.just4fun.pocketscanner.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_scanner.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ScannerFragment : Fragment(), BarcodeCallback {

    val viewModel by viewModel<ScannerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyButton.setOnClickListener {
            MainActivity.start(requireContext())
        }

        scannerLayout.barcodeView.decoderFactory = DefaultDecoderFactory(
            listOf(BarcodeFormat.QR_CODE)
        )
        scannerLayout.decodeSingle(this)
        scannerLayout.resume()

    }

    override fun barcodeResult(result: BarcodeResult?) {
        Timber.d("result : $result")
        val barcodeFormat = result?.barcodeFormat
        val barcode =
            ScannedBarcode(
                type = when (barcodeFormat) {
                    BarcodeFormat.QR_CODE -> BarcodeType.QRCODE
                    else -> throw RuntimeException("Unknown format $barcodeFormat")
                },
                bitmap = result.bitmap,
                text = result.text
            )

        viewModel.onBarcodeScanned(barcode)
    }

    override fun possibleResultPoints(result: MutableList<ResultPoint>?) {
        Timber.d("possibleResultPoints : $result")
    }
}
