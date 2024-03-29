package com.example.energy_monitoring.compose

import com.example.energy_monitoring.R

sealed class DrawerScreens(val title: String, val iconId: Int, val route: String) {
    object Checks : DrawerScreens("Проверки", R.drawable.ic_paste,"checks")
    object ReferenceInfo : DrawerScreens("Документы",  R.drawable.ic_menu_book,"nsi")
    object Settings : DrawerScreens("Настройки",  R.drawable.ic_settings,"settings")
    object Exit : DrawerScreens("Выйти",  R.drawable.ic_logout,"exit")
}