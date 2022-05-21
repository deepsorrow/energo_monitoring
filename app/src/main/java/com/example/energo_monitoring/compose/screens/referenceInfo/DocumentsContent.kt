package com.example.energo_monitoring.compose.screens.referenceInfo

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.energo_monitoring.compose.LoadingIndicator
import com.example.energo_monitoring.compose.data.api.RefDoc
import com.example.energo_monitoring.compose.viewmodels.RefDocsVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DocumentsContent(
    changeTitle: (String) -> Unit,
    viewModel: RefDocsVM = hiltViewModel()
){
    LaunchedEffect(key1 = true) {
        viewModel.getRefDocs()
    }

    val scope = rememberCoroutineScope()

    val onFolderClick: (item: RefDoc) -> Unit = remember {
        {
            scope.launch {
                delay(50)
                viewModel.getCurrentFilesForFile(it)
                changeTitle(it.title)
            }
        }
    }

    val onFileClick: (item: RefDoc) -> Unit = remember {
        {
            scope.launch {
                viewModel.getAndOpenFile(it)
            }
        }
    }

    LoadingIndicator(viewModel.isLoading)

    if (viewModel.refDocs.count() != 0) {
           DocumentsListContent(
               onFolderClick = { onFolderClick(it) },
               onFileClick = { onFileClick(it) }
           )
    }

    BackHandler {
        scope.launch {
            delay(50)
            viewModel.navigateBack()
            changeTitle("Документы")
        }
    }
}

@Preview
@Composable
fun PreviewContent() {
    DocumentsContent({})
}