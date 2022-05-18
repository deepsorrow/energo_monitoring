package com.example.energo_monitoring.compose

import com.example.energo_monitoring.R

sealed class DrawerScreens(val title: String, val iconId: Int, val route: String) {
    object Checks : DrawerScreens("Проверки", R.drawable.ic_paste,"checks")
    object Sync   : DrawerScreens("Документы",  R.drawable.ic_menu_book,"nsi")
    object Exit   : DrawerScreens("Выйти",  R.drawable.ic_logout,"exit")
}