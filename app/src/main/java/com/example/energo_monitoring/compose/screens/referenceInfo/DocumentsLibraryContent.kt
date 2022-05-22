package com.example.energo_monitoring.compose.screens.referenceInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.data.api.RefDoc
import com.example.energo_monitoring.compose.viewmodels.RefDocsVM

@Composable
fun DocumentsLibraryContent(
    changeTitle: (String) -> Unit,
    onFolderClick: (RefDoc) -> Unit,
    viewModel: RefDocsVM = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.getCurrentDocuments()
    }

    if (viewModel.currentChildFiles.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_blur_on),
                contentDescription = "Проверок нет",
                tint = Color.Gray
            )
            Text(
                text = "Сохраненных файлов пока нет\nЗагрузите по кнопке сверху",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 10.dp, bottom = 10.dp)
        ) {
            DocumentsNavigatorContent(
                onFolderClick = onFolderClick,
                onFileClick = { viewModel.openFile(it) },
                changeTitle = changeTitle
            )
        }
    }
}