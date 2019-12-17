package com.san4o.just4fun.pocketscanner

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_result.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ResultFragment : Fragment() {

    val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(this, Observer {
            if (it == ScanningState.SCANNING){
                findNavController().navigate(R.id.action_resultFragment_to_scannerFragment)
            }
        })

        viewModel.scannedBarcode.observe(this, Observer {result ->
            resultText.text = result.text
            resultImage.setImageBitmap(result.bitmap)
        })

        scanButton.setOnClickListener {
            viewModel.startScanning()
        }
    }

}
