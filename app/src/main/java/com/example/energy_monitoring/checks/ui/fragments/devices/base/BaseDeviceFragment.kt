package com.example.energy_monitoring.checks.ui.fragments.devices.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseDeviceFragment : DaggerFragment() {

    var deviceId = 0

    val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        deviceId = arguments?.getInt("deviceId") ?: 0
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}