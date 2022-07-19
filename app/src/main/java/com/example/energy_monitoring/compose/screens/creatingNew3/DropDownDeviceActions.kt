package com.example.energy_monitoring.compose.screens.creatingNew3

import androidx.annotation.DrawableRes
import com.example.energy_monitoring.R

enum class DropDownDeviceActions(val title: String, @DrawableRes val icon: Int) {
    PICK_FROM_CATALOG("Выбрать из каталога", R.drawable.ic_topic),
    SAVE_TO_CATALOG("Сохранить в каталог", R.drawable.ic_save)
}