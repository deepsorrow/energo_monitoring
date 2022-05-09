package com.example.energo_monitoring.checks.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.ui.fragments.screens.Step5_CheckLengthsFragment
import com.example.energo_monitoring.databinding.FragmentCheckLengthBinding
import com.example.energo_monitoring.checks.ui.utils.Utils

class CheckLengthFragment : Fragment() {

    private var deviceId: Int = 0
    private lateinit var binding: FragmentCheckLengthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        deviceId = arguments?.getInt("deviceId") ?: 0
        binding = FragmentCheckLengthBinding.inflate(layoutInflater)
        binding.checkLength = (parentFragment as Step5_CheckLengthsFragment).presenter.getResult(deviceId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utils.initPhotos(this, R.id.photosList, R.layout.list_item_flow_transducers_photo_card, 2)
    }

    companion object {
        @JvmStatic
        fun newInstance(deviceId: Int) =
            CheckLengthFragment().apply {
                arguments = Bundle().apply {
                    putInt("dataId", deviceId)
                }
            }
    }
}