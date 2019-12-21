package com.san4o.just4fun.pocketscanner.scan.process


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.san4o.just4fun.pocketscanner.R
import com.san4o.just4fun.pocketscanner.scan.BarcodeType
import com.san4o.just4fun.pocketscanner.scan.ScanningState
import com.san4o.just4fun.pocketscanner.scan.ScanningViewModel
import kotlinx.android.synthetic.main.fragment_scanner.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class ScannerFragment : Fragment(), BarcodeCallback, ScanningContract.Observer {

    val viewModel by sharedViewModel<ScanningViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observe(this, this)

        scannerLayout.barcodeView.decoderFactory = DefaultDecoderFactory(
            listOf(BarcodeFormat.QR_CODE)
        )
        scannerLayout.decodeSingle(this)
        scannerLayout.resume()

        val timer = Timer()
        timer.schedule(
            object :TimerTask(){
                override fun run() {
                    viewModel.onBarcodeScanned(
                        ScannedBarcode(
                            type = BarcodeType.QRCODE,
                            bitmap = null,
                            text = "http://yandex.ru"
                        )
                    )
                }
            },
            2000
        )
    }

    override fun stateChanged(state: ScanningState) {
        if (state is ScanningState.RESULT){
            findNavController().navigate(R.id.action_scannerFragment_to_resultFragment)
        }
    }

    override fun barcodeResult(result: BarcodeResult?) {
        Timber.d("result : $result")
        val barcodeFormat = result?.barcodeFormat
        viewModel.onBarcodeScanned(
            ScannedBarcode(
                type = when (barcodeFormat) {
                    BarcodeFormat.QR_CODE -> BarcodeType.QRCODE
                    else -> throw RuntimeException("Unknown format $barcodeFormat")
                },
                bitmap = result.bitmap,
                text = result.text
            )
        )
    }

    override fun possibleResultPoints(result: MutableList<ResultPoint>?) {
        Timber.d("possibleResultPoints : $result")
    }
}
