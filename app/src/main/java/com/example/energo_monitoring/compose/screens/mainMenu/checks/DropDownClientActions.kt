package com.example.energo_monitoring.compose.screens.mainMenu.checks

import androidx.annotation.DrawableRes
import com.example.energo_monitoring.R

enum class DropDownClientActions(val title: String, @DrawableRes val icon: Int) {
    SYNC("Синхронизировать", R.drawable.ic_sync),
    REMOVE_LOCALLY("Очистить место", R.drawable.ic_clear),
    REMOVE_GLOBALLY("Удалить", R.drawable.ic_remove),
}