package com.example.energo_monitoring.compose.screens.referenceInfo

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.energo_monitoring.compose.screens.TopBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DocumentsScreen(openDrawer: () -> Job){
    var title by remember { mutableStateOf("Документы") }

    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val openBottomSheet = { scope.launch { state.show() } }
    val closeBottomSheet = { scope.launch { state.hide() } }

    ModalBottomSheetLayout(
        sheetContent = {
            LazyColumn {
                items(BottomSheetActions.values()) {
                    BottomSheetListItem(
                        icon = it.icon,
                        title = it.title,
                        onItemClick = { closeBottomSheet() }
                    )
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp),
        sheetState = state
    ) {
        Scaffold(topBar = {
            TopBar(
                title = title,
                onNavigationIconClicked = { openDrawer() },
                onMoreButtonClicked = { openBottomSheet() }
            )
        }, content = {
            DocumentsContent(changeTitle = { newTitle: String -> title = newTitle })
        })
    }

}

@Preview
@Composable
fun PreviewDocuments(){
    DocumentsScreen {
        Job()
    }
}