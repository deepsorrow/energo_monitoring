package com.example.energo_monitoring.compose.screens.referenceInfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energo_monitoring.compose.viewmodels.RefDocsVM

@Composable
fun DocumentsContent(viewModel: RefDocsVM = hiltViewModel()){
    LaunchedEffect(key1 = true) {

    }
}

@Preview
@Composable
fun PreviewContent() {
    DocumentsContent()
}