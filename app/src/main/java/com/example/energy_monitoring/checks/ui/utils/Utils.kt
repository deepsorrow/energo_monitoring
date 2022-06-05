package com.example.energy_monitoring.checks.ui.utils

import com.example.energy_monitoring.checks.data.db.ResultDataDatabase
import com.example.energy_monitoring.checks.data.db.OtherInfo
import android.content.Context
import java.io.File

object Utils {
    /**
     * Записать прогресс для восстанавления из главной страницы при необходимости
     * @param dataId - идентификатор данных для отправки на сервер
     */
    fun logProgress(context: Context?, currentScreen: Int, dataId: Int) {
        val db = ResultDataDatabase.getDatabase(context)
        var otherInfo = db.resultDataDAO().getOtherInfo(dataId)
        if (otherInfo == null) {
            otherInfo = OtherInfo(dataId)
        }
        otherInfo.completedScreens = otherInfo.completedScreens.coerceAtLeast(currentScreen)
        otherInfo.currentScreen = currentScreen
        db.resultDataDAO().insertOtherInfo(otherInfo)
    }

    fun getDataIdFolder(context: Context, dataId: Int) = File(context.filesDir, "$dataId").also { it.mkdirs() }
}