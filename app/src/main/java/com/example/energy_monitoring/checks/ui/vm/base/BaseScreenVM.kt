package com.example.energy_monitoring.checks.ui.vm.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.energy_monitoring.checks.ui.utils.Utils
import com.example.energy_monitoring.checks.di.DATA_ID
import com.example.energy_monitoring.checks.domain.repo.ResultDataRepository
import javax.inject.Inject
import javax.inject.Named

abstract class BaseScreenVM(
    private val screenNumber: Int,
    application: Application,
) : AndroidViewModel(application) {

    @JvmField
    @Inject
    @Named(DATA_ID)
    var dataId: Int = 0

    @Inject
    lateinit var repository: ResultDataRepository

    val context: Context
        get() = getApplication()

    var initialized: Boolean = false

    open fun initialize() {
        logProgress()
    }

    private fun logProgress() = Utils.logProgress(context, screenNumber, dataId)
}