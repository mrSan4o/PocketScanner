package com.san4o.just4fun.pocketscanner.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.san4o.just4fun.pocketscanner.R
import com.san4o.just4fun.pocketscanner.databinding.FragmentResultBinding
import com.san4o.just4fun.pocketscanner.presentation.*
import kotlinx.android.synthetic.main.fragment_result.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class ResultFragment : Fragment(),
    ScannedResultContract.Observer {

    val viewModel by sharedViewModel<ScanningViewModel>()

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

        binding.state = viewModel.viewState
        binding.interactor = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun stateChanged(state: ScanningState) {
        if (state == ScanningState.SCANNING) {
            findNavController().navigate(R.id.action_resultFragment_to_scannerFragment)
        }
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

class ActionTextWatcher(private val action: () -> Unit) : TextWatcher {
    val delay: Long = 1000
    var startAtTime: Long = 0
    var timer = Timer()
    override fun afterTextChanged(s: Editable?) {
        if (System.currentTimeMillis() - startAtTime > delay) {
            timer.cancel()
            timer = Timer()
        }

        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    action.invoke()
                }
            },
            delay
        )
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}
