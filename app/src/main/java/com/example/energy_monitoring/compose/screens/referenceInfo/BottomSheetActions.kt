package com.example.energy_monitoring.compose.screens.referenceInfo

import androidx.annotation.DrawableRes
import com.example.energy_monitoring.R

enum class BottomSheetActions(val title: String, @DrawableRes val icon: Int) {
    SYNC("Загрузить с облака", R.drawable.ic_cloud),
    ADD_FOLDER("Создать папку", R.drawable.ic_new_folder),
    ADD_FILE("Добавить файл", R.drawable.ic_upload_file),
    REMOVE_ALL("Удалить все", R.drawable.ic_clear),
}