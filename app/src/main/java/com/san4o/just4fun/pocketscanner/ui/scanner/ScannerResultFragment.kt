package com.san4o.just4fun.pocketscanner.ui.scanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.san4o.just4fun.pocketscanner.R
import com.san4o.just4fun.pocketscanner.databinding.FragmentResultBinding
import com.san4o.just4fun.pocketscanner.presentation.result.*
import com.san4o.just4fun.pocketscanner.presentation.scanner.ScannerViewModel
import com.san4o.just4fun.pocketscanner.ui.MainActivity
import com.san4o.just4fun.pocketscanner.ui.base.ActionTextWatcher
import com.san4o.just4fun.pocketscanner.ui.base.toastLong
import com.san4o.just4fun.pocketscanner.ui.navigate
import kotlinx.android.synthetic.main.fragment_result.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ResultFragment : Fragment(),
    ScannedResultContract.Observer {

    private val scannerViewModel: ScannerViewModel by sharedViewModel()

    lateinit var viewModel: ScanningResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentResultBinding>(
            inflater,
            R.layout.fragment_result,
            container,
            false
        )

        val model: ScanningResultViewModel by viewModel {
            parametersOf(BarcodeParams.getFromArguments(arguments))
        }
        viewModel = model

        binding.state = viewModel.viewState
        binding.interactor = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scanButton.setOnClickListener {
            navigate(ScannerNavigation.Scanner)
        }

        historyButton.setOnClickListener {
            MainActivity.start(requireContext())
        }

        viewModel.observe(this, this)

        title.addTextChangedListener(ActionTextWatcher {
            viewModel.saveTitleState()
        })
    }

    override fun openBarcodeResult(data: OpenParams) {
        val url = data.url
        if (url.isEmpty()) return

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    override fun notifyMessage(message: String) {
        toastLong(message)
    }

    override fun shareBarcodeResult(data: ShareParams) {
        val scannedResult = data.url

        if (scannedResult.isBlank()) return

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Отсканированный результат")
        var shareMessage = scannedResult
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Послать результат"))
    }
}