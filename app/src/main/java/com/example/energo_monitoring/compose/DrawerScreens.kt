package com.example.energo_monitoring.compose

import com.example.energo_monitoring.R

sealed class DrawerScreens(val title: String, val iconId: Int, val route: String) {
    object Checks : DrawerScreens("Проверки", R.drawable.ic_paste,"checks")
    object Sync   : DrawerScreens("Синхронизация\nс облаком",  R.drawable.ic_cloud_sync,"sync")
    object Exit   : DrawerScreens("Выйти",  R.drawable.ic_logout,"exit")
}