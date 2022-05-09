package com.example.energo_monitoring.checks.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.energo_monitoring.R
import com.example.energo_monitoring.databinding.FragmentStep3GeneralInspectionBinding
import com.example.energo_monitoring.checks.Utils
import com.example.energo_monitoring.checks.activities.CheckMainActivity
import com.example.energo_monitoring.checks.presenters.GeneralInspectionPresenter
import com.example.energo_monitoring.checks.presenters.utilities.SharedPreferencesManager
import com.example.energo_monitoring.checks.viewmodel.GeneralInspectionViewModel

class Step3_GeneralInspectionFragment : Fragment() {

    private var dataId = 0
    private lateinit var binding: FragmentStep3GeneralInspectionBinding
    private lateinit var presenter: GeneralInspectionPresenter
    private var lightIsOk: Boolean? = null
    private var sanPinIsOk: Boolean? = null
    val viewModel: GeneralInspectionViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStep3GeneralInspectionBinding.inflate(layoutInflater)

        binding.clientInfo = SharedPreferencesManager.getClientDataBundle(requireContext()).clientInfo

        presenter = GeneralInspectionPresenter(requireContext())

        val activity = requireActivity() as CheckMainActivity
        dataId = activity.dataId
        activity.insertDestinationListener(R.id.step3_GeneralInspectionFragment) {
            presenter.insertDataToDb(dataId, lightIsOk, sanPinIsOk, binding.commentEditText.text.toString())
        }

        Utils.logProgress(requireContext(), 3, dataId)

        addToggleGroupListener()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addToggleGroupListener(){
        binding.lightToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if(isChecked) {
                when (checkedId) {
                    R.id.light_yes_button -> lightIsOk = true
                    R.id.light_no_button -> lightIsOk = false
                }
            } else {
                lightIsOk = null
            }
        }
        binding.sanPinToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if(isChecked) {
                when (checkedId) {
                    R.id.sanPin_yes_button -> sanPinIsOk = true
                    R.id.sanPin_no_button -> sanPinIsOk = false
                }
            } else {
                sanPinIsOk = null
            }
        }
    }
}