package com.example.energo_monitoring.compose.screens.referenceInfo

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energo_monitoring.compose.data.api.RefDoc
import com.example.energo_monitoring.compose.screens.TopBar
import com.example.energo_monitoring.compose.viewmodels.RefDocsVM
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
@ViewModelScoped
fun DocumentsScreen(
    openDrawer: () -> Job,
    viewModel: RefDocsVM = hiltViewModel()
){
    var title by remember { mutableStateOf("Документы") }

    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    var showGetNewFiles by remember { mutableStateOf(false) }

    val openBottomSheet = { scope.launch { state.show() } }
    val closeBottomSheet = { scope.launch { state.hide() } }

    val context = LocalContext.current

    val onBottomOptionClicked = { selectedAction: BottomSheetActions ->
        when (selectedAction) {
            BottomSheetActions.SYNC -> showGetNewFiles = true
            else -> Toast.makeText(context, "Функционал в разработке", Toast.LENGTH_SHORT).show()
            //BottomSheetActions.ADD_FOLDER -> viewModel.createNewFolder()
        }
        closeBottomSheet()
    }

    val changeTitle = { newTitle: String -> title = newTitle }

    val onFolderClick: (item: RefDoc) -> Unit = remember {
        {
            scope.launch {
                delay(50)
                viewModel.getCurrentFilesForFile(it)
                changeTitle(it.title)
            }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            LazyColumn {
                items(BottomSheetActions.values()) {
                    BottomSheetListItem(
                        item = it,
                        onItemClick = { selectedAction -> onBottomOptionClicked(selectedAction) }
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
            if (showGetNewFiles) {
                DownloadNewFilesContent(
                    onDismissRequest = { showGetNewFiles = false },
                    changeTitle = changeTitle,
                    onFolderClick = onFolderClick
                )
            } else {
                changeTitle("Документы")
                DocumentsLibraryContent(
                    changeTitle = changeTitle,
                    onFolderClick = onFolderClick
                )
            }
        })
    }

}

@Preview
@Composable
fun PreviewDocuments(){
    DocumentsScreen({ Job() })
}