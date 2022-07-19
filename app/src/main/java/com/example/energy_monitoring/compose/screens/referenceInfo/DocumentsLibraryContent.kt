package com.example.energy_monitoring.compose.screens.referenceInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energy_monitoring.R
import com.example.energy_monitoring.compose.data.api.RefDoc
import com.example.energy_monitoring.compose.screens.NoDataPlaceholder
import com.example.energy_monitoring.compose.viewmodels.RefDocsVM

@Composable
fun DocumentsLibraryContent(
    changeTitle: (String) -> Unit,
    onFolderClick: (RefDoc) -> Unit,
    viewModel: RefDocsVM
) {
    LaunchedEffect(key1 = true) {
        viewModel.getCurrentDocuments()
    }

    if (viewModel.currentChildFiles.isEmpty()) {
        if (viewModel.refDocs.isEmpty()) {
            NoDataPlaceholder(
                modifier = Modifier.fillMaxSize(),
                text = "Сохраненных файлов пока нет\nЗагрузите или добавьте по кнопке сверху",
                iconRes = R.drawable.ic_blur_on
            )
        } else {
            NoDataPlaceholder(
                modifier = Modifier.fillMaxSize(),
                text = "В этой папке пока нет файлов",
                iconRes = R.drawable.ic_blur_on
            )
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 5.dp, bottom = 10.dp)
        ) {
            DocumentsNavigatorContent(
                onFolderClick = onFolderClick,
                onFileClick = { viewModel.openFile(it) },
                changeTitle = changeTitle,
                viewModel = viewModel
            )
        }
    }
}