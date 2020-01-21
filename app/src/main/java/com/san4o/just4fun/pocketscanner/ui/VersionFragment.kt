package com.san4o.just4fun.pocketscanner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import com.san4o.just4fun.pocketscanner.BuildConfig
import com.san4o.just4fun.pocketscanner.R
import com.san4o.just4fun.pocketscanner.databinding.FragmentAboutBinding

class VersionFragment : AppCompatDialogFragment() {
    lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAboutBinding>(
            inflater,
            R.layout.fragment_about,
            container,
            true
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val version = BuildConfig.VERSION_NAME
        val code = BuildConfig.VERSION_CODE
        binding.versionValue.text = "$version ($code)"

        dialog?.setTitle(R.string.about_text)

        binding.okButton.setOnClickListener {
            dismiss()
        }
    }
}