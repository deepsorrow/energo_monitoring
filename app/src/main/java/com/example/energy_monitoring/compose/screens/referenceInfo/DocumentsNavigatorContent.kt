package com.example.energy_monitoring.compose.screens.referenceInfo

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energy_monitoring.compose.LoadingIndicator
import com.example.energy_monitoring.compose.data.api.RefDoc
import com.example.energy_monitoring.compose.viewmodels.RefDocsVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DocumentsNavigatorContent(
    onFolderClick: (item: RefDoc) -> Unit,
    onFileClick: (item: RefDoc) -> Unit,
    changeTitle: (String) -> Unit,
    viewModel: RefDocsVM
) {
    val scope = rememberCoroutineScope()

    LoadingIndicator(viewModel.isLoading)

    DocumentsListContent(
        onFolderClick = { onFolderClick(it) },
        onFileClick = { onFileClick(it) },
        viewModel = viewModel
    )

    BackHandler {
        scope.launch {
            delay(50)
            viewModel.navigateBack()
            changeTitle(viewModel.currentFolder.title.ifEmpty { "Документы" })
        }
    }
}