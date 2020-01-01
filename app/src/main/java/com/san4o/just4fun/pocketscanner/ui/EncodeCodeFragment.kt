package com.san4o.just4fun.pocketscanner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.san4o.just4fun.pocketscanner.R
import com.san4o.just4fun.pocketscanner.presentation.encoder.EncodeCodeViewModel
import com.san4o.just4fun.pocketscanner.ui.base.setVisibile
import com.san4o.just4fun.pocketscanner.ui.base.share
import kotlinx.android.synthetic.main.fragment_encode_code.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EncodeCodeFragment : Fragment() {

    private val viewModel: EncodeCodeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_encode_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shareBarcodeImage.setVisibile(false)

        encodeText.addTextChangedListener(
            afterTextChanged = {
                clearAllTextImage.setVisibile(it?.isEmpty() != true)
            }
        )
        clearAllTextImage.setOnClickListener {
            encodeText.setText("")
            shareBarcodeImage.setVisibile(false)
        }

        viewModel.bitmap.observe(this, Observer { bitmap ->
            if (bitmap != null) {
                shareBarcodeImage.setVisibile(true)
                resultImage.setImageBitmap(bitmap)
            } else {
                resultImage.setImageBitmap(null)
                shareBarcodeImage.setVisibile(false)
            }
        })
        viewModel.share.observe(this, Observer {
            share(it)
        })

        generateButton.setOnClickListener {
            val text = encodeText.text.toString()

            viewModel.onTextEntered(text)
        }

        shareBarcodeImage.setOnClickListener {
            viewModel.onShareResult()
        }
    }
}
