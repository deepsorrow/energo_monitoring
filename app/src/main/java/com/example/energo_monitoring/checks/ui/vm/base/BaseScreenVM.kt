package com.example.energo_monitoring.checks.ui.vm.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.energo_monitoring.checks.ui.utils.Utils
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase
import com.example.energo_monitoring.checks.di.DATA_ID
import com.example.energo_monitoring.checks.domain.repo.ResultDataRepository
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
    lateinit var db: ResultDataDatabase

    @Inject
    lateinit var repository: ResultDataRepository

    val context: Context
        get() = getApplication()

    init {
        logProgress()
    }

    abstract fun initialize()

    private fun logProgress() = Utils.logProgress(context, screenNumber, dataId)
}