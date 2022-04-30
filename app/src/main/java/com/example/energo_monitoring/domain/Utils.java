package com.example.energo_monitoring.domain;

import android.content.Context;

import com.example.energo_monitoring.data.db.OtherInfo;
import com.example.energo_monitoring.data.db.ResultDataDatabase;

public class Utils {
    /**
     * Записать прогресс для восстанавления из главной страницы при необходимости
     * @param dataId - идентификатор данных для отправки на сервер
     */
    public static void logProgress(Context context, int currentScreen, int dataId) {
        ResultDataDatabase db = ResultDataDatabase.getDatabase(context);
        OtherInfo otherInfo = db.resultDataDAO().getOtherInfo(dataId);
        if (otherInfo == null)
            otherInfo = new OtherInfo(dataId);

        otherInfo.completedScreens = Math.max(otherInfo.completedScreens, currentScreen);
        otherInfo.currentScreen = currentScreen;

        db.resultDataDAO().insertOtherInfo(otherInfo);
    }
}
