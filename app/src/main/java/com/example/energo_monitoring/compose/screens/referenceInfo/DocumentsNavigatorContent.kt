package com.example.energo_monitoring.compose.screens.referenceInfo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.LoadingIndicator
import com.example.energo_monitoring.compose.data.api.RefDoc
import com.example.energo_monitoring.compose.viewmodels.RefDocsVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DocumentsNavigatorContent(
    onFolderClick: (item: RefDoc) -> Unit,
    onFileClick: (item: RefDoc) -> Unit,
    changeTitle: (String) -> Unit,
    viewModel: RefDocsVM = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    LoadingIndicator(viewModel.isLoading)

    DocumentsListContent(
        onFolderClick = { onFolderClick(it) },
        onFileClick = { onFileClick(it) }
    )

    BackHandler {
        scope.launch {
            delay(50)
            viewModel.navigateBack()
            changeTitle("Документы")
        }
    }
}