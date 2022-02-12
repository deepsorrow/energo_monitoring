package com.example.feature_create_new_data.data

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import com.example.feature_create_new_data.R

sealed class DrawerScreens(val title: String, val iconId: Int, val route: String) {
    object Checks : DrawerScreens("Проверки", R.drawable.ic_baseline_content_paste_go_24,"checks")
    object Sync   : DrawerScreens("Синхронизация\nс сервером",  R.drawable.ic_baseline_cloud_sync_24,"sync")
    object Exit   : DrawerScreens("Выйти",  R.drawable.ic_baseline_logout_24,"exit")
}