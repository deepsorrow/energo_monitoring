package com.example.energy_monitoring.compose.screens.creatingNew2

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energy_monitoring.R
import com.example.energy_monitoring.compose.screens.DataGroup
import com.example.energy_monitoring.compose.screens.FloatingPlusButton
import com.example.energy_monitoring.compose.screens.NoDataPlaceholder
import com.example.energy_monitoring.compose.viewmodels.ClientInfoViewModel
import dagger.hilt.android.scopes.ViewModelScoped

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@ViewModelScoped
fun CreatingNew2Content(
    viewModel: ClientInfoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var showAlertBrokenImage by remember { mutableStateOf(false) }

    var actionAfterPermissionCheck by remember { mutableStateOf({}) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            actionAfterPermissionCheck()
        }
    }
    var openPickFromCatalogDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                val path = viewModel.getRealPathFromUri(context, uri)
                if (viewModel.projectFiles.find { it.path == path } == null) {
                    val newBitmap = viewModel.getBitmapFromUri(context, uri)
                    if (newBitmap == null) {
                        showAlertBrokenImage = true
                        return@rememberLauncherForActivityResult
                    }

                    if (path != null) {
                        viewModel.addPhoto(path, newBitmap)
                    }
                } else {
                    Toast.makeText(context, "Этот файл уже был добавлен!", Toast.LENGTH_LONG).show()
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        DataGroup("Документы", viewModel.projectDropDownItems) {
            when (it.title) {
                DropDownProjectActions.PICK_FROM_CATALOG.title -> openPickFromCatalogDialog = true
            }
        }
        Box(contentAlignment = Alignment.Center) {
            if (viewModel.projectFiles.isEmpty()) {
                NoDataPlaceholder(
                    modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                    text = "Файлов пока нет\n" +
                            "Добавьте новый или выберите из каталога",
                    iconRes = R.drawable.ic_blur_on
                )
            }
            Box(contentAlignment = Alignment.BottomEnd) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 410.dp, max = 450.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .border(1.dp, Color.LightGray)
                ) {
                    itemsIndexed(items = viewModel.projectFiles) { index, file ->
                        if (index == 0) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.showFilePreview = true
                                    viewModel.filePreview = file
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (file.bitmap != null) {
                                Image(
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .padding(start = 5.dp)
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(percent = 30))
                                        .border(0.0.dp, Color.Gray, RoundedCornerShape(percent = 30)),
                                    bitmap = file.bitmap!!.asImageBitmap(),
                                    contentDescription = "Файл проекта",
                                )
                            }
                            Text(
                                modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                                text = file.title
                            )
                        }

                        if (index != viewModel.projectFiles.count() - 1) {
                            Divider(
                                modifier = Modifier
                                    .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                                    .height(0.2.dp)
                                    .background(color = Color.LightGray)
                            )
                        } else {
                            // Stub view
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            )
                        }
                    }
                }

                FloatingPlusButton(Modifier.padding(end = 25.dp, bottom = 5.dp)) { launcher.launch("image/*") }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp, start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary),
                checked = viewModel.matchesConditions,
                onCheckedChange = {
                    viewModel.matchesConditions = it
                }
            )

            Text(
                text = "Соответствие нормативным требованиям",
                color = Color.DarkGray,
                fontSize = 16.sp
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 5.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            singleLine = true,
            label = {
                Text(text = "Комментарий")
            },
            value = viewModel.commentaryFiles,
            onValueChange = { viewModel.commentaryFiles = it },
        )

        viewModel.syncBitmapWithPhotos(context)

        if (viewModel.showFilePreview) {
            Dialog(
                properties = DialogProperties(usePlatformDefaultWidth = false),
                onDismissRequest = { viewModel.showFilePreview = false }
            ) {
                Surface(
                    modifier = Modifier.padding(
                        top = 10.dp,
                        bottom = 10.dp,
                        start = 10.dp,
                        end = 10.dp
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 0.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(247, 247, 247, 206))
                                    .padding(top = 8.dp, bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.padding(start = 25.dp, end = 25.dp),
                                    text = viewModel.filePreview.title,
                                    textAlign = TextAlign.Center,
                                    fontSize = 17.sp
                                )
                            }
                        }
                        Column {
                            viewModel.filePreview.bitmap?.let {
                                Image(
                                    modifier = Modifier.fillMaxWidth().weight(14f),
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = "Предпросмотр файла"
                                )
                            }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(247, 247, 247, 206))
                                .weight(1f),
                                horizontalArrangement = Arrangement.SpaceEvenly) {
                                IconButton(
                                    modifier = Modifier.padding(start = 5.dp),
                                    onClick = { }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_edit),
                                        contentDescription = "Редактировать"
                                    )
                                }
                                IconButton(
                                    modifier = Modifier.padding(start = 5.dp),
                                    onClick = {
                                        viewModel.removeImageIndex =
                                            viewModel.projectFiles.indexOf(viewModel.filePreview)
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_trash),
                                        contentDescription = "Удалить"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        BrokenImageAlertDialog(showAlertBrokenImage) { showAlertBrokenImage = false }

        if (viewModel.removeImageIndex != null) {
            DeleteImageAlertDialog(
                onConfirm = {
                    viewModel.projectFiles.removeAt(viewModel.removeImageIndex!!)
                    viewModel.removeImageIndex = null
                    viewModel.showFilePreview = false
                },
                onDismiss = {
                    viewModel.removeImageIndex = null
                }
            )
        }
    }
}

@Composable
fun BrokenImageAlertDialog(showAlertBrokenImage: Boolean, onDismiss: () -> Unit) {
    if (showAlertBrokenImage) {
        AlertDialog(
            text = {
                Text(text = "Невозможно получить изображение, пожалуйста, выберите другое")
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                Text(
                    color = MaterialTheme.colors.primary,
                    text = "ОК",
                    modifier = Modifier
                        .clickable { onDismiss() }
                        .padding(10.dp)
                )
            },
            dismissButton = { },
            title = {
                Text(text = "Изображение повреждено")
            },
        )
    }
}

@Composable
fun DeleteImageAlertDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        text = {
            Text(text = "Вы действительно хотите убрать это изображение?", fontSize = 16.sp)
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                color = Color(185, 91, 91, 255),
                text = "Убрать",
                modifier = Modifier
                    .clickable { onConfirm() }
                    .padding(10.dp)
            )
        },
        dismissButton = {
            Text(
                color = Color.DarkGray,
                text = "Оставить",
                modifier = Modifier
                    .clickable { onDismiss() }
                    .padding(10.dp)
            )
        },
        title = {
            Text(modifier = Modifier.fillMaxWidth(), text = "Убрать изображение", textAlign = TextAlign.Center)
        },
    )
}

@Composable
@Preview
fun CreatingNew2ContentPreview() {
    //CreatingNew2Content(ClientInfoViewModel())
}

