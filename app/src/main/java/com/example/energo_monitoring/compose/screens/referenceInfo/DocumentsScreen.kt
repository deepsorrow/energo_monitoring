package com.example.energo_monitoring.compose.screens.referenceInfo

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.energo_monitoring.compose.screens.TopBar
import kotlinx.coroutines.Job

@Composable
fun DocumentsScreen(openDrawer: () -> Job){
    Scaffold(topBar = {
        TopBar(
            title = "Документы",
            onNavigationIconClicked = { openDrawer() }
        )
    }, content = {
        DocumentsContent()
    })
}

@Preview
@Composable
fun PreviewDocuments(){
    DocumentsScreen {
        Job()
    }
}