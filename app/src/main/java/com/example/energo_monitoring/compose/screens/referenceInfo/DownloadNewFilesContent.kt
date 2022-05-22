package com.example.energo_monitoring.compose.screens.referenceInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.data.api.RefDoc
import com.example.energo_monitoring.compose.viewmodels.RefDocsVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DownloadNewFilesContent(
    changeTitle: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onFolderClick: (RefDoc) -> Unit,
    viewModel: RefDocsVM = hiltViewModel()
){
    LaunchedEffect(key1 = true) {
        viewModel.getRefDocs()
    }

    val scope = rememberCoroutineScope()

    val onFileClick: (item: RefDoc) -> Unit = remember {
        {
            scope.launch {
                viewModel.downloadFile(it)
            }
        }
    }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(bottom = 10.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(247, 247, 247, 206))
                        .padding(top = 8.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Выберите файл для сохранения",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                DocumentsNavigatorContent(
                    onFolderClick = onFolderClick,
                    onFileClick = onFileClick,
                    changeTitle = changeTitle
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewContent() {
    DownloadNewFilesContent({}, {}, {})
}