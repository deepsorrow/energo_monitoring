package com.example.energy_monitoring.compose.screens.referenceInfo

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energy_monitoring.checks.ui.utils.Utils
import com.example.energy_monitoring.checks.ui.utils.Utils.getRefDocsFolder
import com.example.energy_monitoring.compose.data.api.RefDoc
import com.example.energy_monitoring.compose.screens.TopBar
import com.example.energy_monitoring.compose.viewmodels.RefDocsVM
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
    val context = LocalContext.current
    var title by remember { mutableStateOf("Документы") }

    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    var showGetNewFiles by remember { mutableStateOf(false) }

    val openBottomSheet = { scope.launch { state.show() } }
    val closeBottomSheet = { scope.launch { state.hide() } }
    
    var showAddNewFolder by remember { mutableStateOf(false) }

    val pickFileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            val path = Utils.getRealPathFromUri(context, uri, getRefDocsFolder(context))
            viewModel.createNewFile(path)
        }
    }

    val changeTitle = { newTitle: String -> title = newTitle }

    val onBottomOptionClicked = { selectedAction: BottomSheetAction ->
        when (selectedAction) {
            BottomSheetAction.SYNC -> showGetNewFiles = true
            BottomSheetAction.ADD_FOLDER -> showAddNewFolder = true
            BottomSheetAction.ADD_FILE -> { pickFileLauncher.launch("*/*") }
            BottomSheetAction.REMOVE_ALL ->  {
                viewModel.deleteAllDocs(context)
                changeTitle("Документы")
            }
        }
        closeBottomSheet()
    }

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
                items(BottomSheetAction.values()) {
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
                    onFolderClick = onFolderClick,
                    viewModel = viewModel
                )
            } else {
                DocumentsLibraryContent(
                    changeTitle = changeTitle,
                    onFolderClick = onFolderClick,
                    viewModel = viewModel
                )
            }
        })
    }
    
    if (showAddNewFolder) {
        AddNewFolderDialog(
            value = viewModel.newFolderName,
            onValueChanged = { viewModel.newFolderName = it },
            onConfirm = { viewModel.createNewFolder().also { showAddNewFolder = false } },
            onDismiss = { showAddNewFolder = false }
        )
    }

    BackHandler {
        scope.launch {
            delay(50)
            viewModel.navigateBack()
            changeTitle(viewModel.currentFolder.title.ifEmpty { "Документы" })
        }
    }
}

@Preview
@Composable
fun PreviewDocuments(){
    DocumentsScreen({ Job() })
}