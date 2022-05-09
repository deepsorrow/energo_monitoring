package com.example.energo_monitoring.checks.ui.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.energo_monitoring.R
import com.example.energo_monitoring.databinding.FragmentStep5CheckLengthsBinding
import com.example.energo_monitoring.checks.ui.utils.Utils
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase
import com.example.energo_monitoring.checks.ui.adapters.LengthsStateAdapter
import com.example.energo_monitoring.checks.ui.presenters.CheckLengthOfStraightLinesPresenter
import com.example.energo_monitoring.checks.ui.viewmodel.CheckLengthViewModel
import com.google.android.material.tabs.TabLayoutMediator

class Step5_CheckLengthsFragment : Fragment() {

    private lateinit var binding: FragmentStep5CheckLengthsBinding
    lateinit var presenter: CheckLengthOfStraightLinesPresenter
    private lateinit var model: CheckLengthViewModel
    private lateinit var adapter: LengthsStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStep5CheckLengthsBinding.inflate(layoutInflater)

        val context = requireContext()

        val activity = requireActivity() as CheckMainActivity
        val dataId = activity.dataId
        //activity.insertDestinationListener(R.id.step5_CheckLengthsFragment) {
//            val currentLengthBefore: String = binding.lengthBefore.text.toString()
//            val currentLengthAfter: String = binding.lengthAfter.text.toString()
//            presenter.saveLengths(currentLengthBefore, currentLengthAfter)
//            if (model.currentDeviceId.value != null)
//                presenter.saveAndSetPhotos(model.currentDeviceId.value!!)
//
//            presenter.insertDataToDb(context)
        //}

        Utils.logProgress(requireContext(), 5, dataId)

        val devices = ResultDataDatabase.getDatabase(context).resultDataDAO().getDeviceFlowTransducers(dataId)

        presenter = CheckLengthOfStraightLinesPresenter(this, devices.orEmpty(), dataId)

//        model = ViewModelProvider(this)[CheckLengthViewModel::class.java]
//
//        initLists()
//
//        val currentIdObserver = Observer { integer: Int? ->
//            val currentLengthBefore: String = binding.lengthBefore.text.toString()
//            val newLengthBefore = presenter.saveAndGetLengthBefore(integer!!, currentLengthBefore)
//            val currentLengthAfter: String = binding.lengthAfter.text.toString()
//            val newLengthAfter = presenter.saveAndGetLengthAfter(integer, currentLengthAfter)
//            if (currentLengthBefore.isNotEmpty() && currentLengthAfter.isNotEmpty() && presenter.photos.size > 1) // complete
//                presenter.getResult(presenter.currentId)
//                    .setIcon(R.drawable.ic_baseline_check_circle_24) else if (currentLengthBefore.isNotEmpty() || currentLengthAfter.isNotEmpty() || presenter.photos.size > 1) // touched
//                presenter.getResult(presenter.currentId)
//                    .setIcon(R.drawable.ic_baseline_incomplete_circle_24) else  // untouched
//                presenter.getResult(presenter.currentId)
//                    .setIcon(R.drawable.ic_baseline_trip_origin_24)
//            deviceAdapter!!.notifyItemChanged(presenter.currentId)
//            binding.lengthBefore.setText(newLengthBefore)
//            binding.lengthAfter.setText(newLengthAfter)
//            presenter.saveAndSetPhotos(integer)
//            presenter.currentId = integer
//        }
//
//        model.currentDeviceId.value = 0
//        model.currentDeviceId.observe(activity, currentIdObserver)
//        presenter.setViewModel(model)

        adapter = LengthsStateAdapter(this)
        binding.checkLengthsPager.adapter = adapter
        TabLayoutMediator(binding.checkLengthTabs, binding.checkLengthsPager) { tab, position ->
            val device = presenter.devices[position]
            tab.text = " ${device?.deviceName}\n#${device?.deviceNumber}"
        }.attach()

        return binding.root
    }
}