package com.san4o.just4fun.pocketscanner


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import kotlinx.android.synthetic.main.fragment_scanner.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class ScannerFragment : Fragment(), BarcodeCallback {

    val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(this, Observer {
            if (it == ScanningState.RESULT){
                findNavController().navigate(R.id.action_scannerFragment_to_resultFragment)
            }
        })

        scannerLayout.barcodeView.decoderFactory = DefaultDecoderFactory(
            listOf(BarcodeFormat.QR_CODE)
        )
        scannerLayout.decodeSingle(this)
        scannerLayout.resume()
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
