package com.example.energy_monitoring.compose.screens.referenceInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energy_monitoring.compose.data.api.RefDoc
import com.example.energy_monitoring.compose.viewmodels.RefDocsVM

@Composable
fun DocumentsListContent(viewModel: RefDocsVM = hiltViewModel(), onFolderClick: (item: RefDoc) -> Unit, onFileClick: (item: RefDoc) -> Unit) {
    LazyColumn {
        itemsIndexed(items = viewModel.currentChildFiles) { index, it ->
            if (it.isFolder) {
                DocumentFolder(folder = it, onClick = { onFolderClick(it) })
            } else {
                DocumentFile(file = it, onClick = { onFileClick(it) })
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
}