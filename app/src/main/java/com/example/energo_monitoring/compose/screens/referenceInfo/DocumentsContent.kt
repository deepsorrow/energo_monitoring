package com.example.energo_monitoring.compose.screens.referenceInfo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.viewmodels.RefDocsVM
import kotlinx.coroutines.Job

@Composable
fun DocumentsContent(
    changeTitle: (String) -> Unit,
    viewModel: RefDocsVM = hiltViewModel()
){
    LaunchedEffect(key1 = true) {
        viewModel.getRefDocs()
    }
    LazyColumn {
        itemsIndexed(items = viewModel.currentChildFiles) { index, it ->
            if (it.isFolder) {
                DocumentFolder(folder = it, onClick = {
                    viewModel.getCurrentFilesForFile(it)
                    changeTitle(it.title)
                })
            } else {
                DocumentFile(file = it, onClick = {  })
            }

            if (index != viewModel.currentChildFiles.count()) {
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .height(0.5.dp)
                )
            }
        }
    }
    BackHandler {
        viewModel.navigateBack()
        changeTitle("Документы")
    }
}

@Preview
@Composable
fun PreviewContent() {
    DocumentsContent({})
}