package com.example.energo_monitoring.compose.screens.creatingNew2

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.screens.creatingNew1.CreatingLongTextField
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreatingNew2Content(
    viewModel: ClientInfoViewModel
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            // .verticalScroll(rememberScrollState()),
            ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 15.dp, start = 20.dp),
            text = "Прикрипите одну или более схем",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        val bitmap = remember {
            mutableStateListOf<Bitmap>()
        }

        if (bitmap.size != viewModel.photos.size) {
            var i = 0

            while (i < viewModel.photos.size) {
                val uri = viewModel.photos[i]

                val newBitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val decoder = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(decoder)
                }

                if (newBitmap == null) {
                    viewModel.photos.removeAt(i)
                } else {
                    bitmap.add(newBitmap)
                    i++
                }
            }
        }

        var alertBrokenImage by remember { mutableStateOf(false) }

        if (alertBrokenImage) {
            AlertDialog(
                text = {
                    Text(text = "Невозможно получить данные о изображении, пожалуйста, выберите другое изображение")
                },

                onDismissRequest = { alertBrokenImage = false },

                confirmButton = {
                    Text(
                        color = Color.Cyan,
                        text = "ОК",
                        modifier = Modifier
                            .clickable {
                                alertBrokenImage = false
                            }
                            .padding(10.dp)
                    )
                },

                dismissButton = { },

                title = {
                    Text(text = "Изображение повреждено")
                },
            )
        }

        val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = {
            if (it != null) {
                if (!viewModel.photos.contains(it)) {
                    val newBitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    } else {
                        val decoder = ImageDecoder.createSource(context.contentResolver, it)
                        ImageDecoder.decodeBitmap(decoder)
                    }

                    if (newBitmap == null) {
                        alertBrokenImage = true
                        return@rememberLauncherForActivityResult
                    }

                    viewModel.photos.add(it)
                    bitmap.add(newBitmap)
                }
            }
        })

        var removeImageIndex by remember {
            mutableStateOf<Int?>(null)
        }

        LazyVerticalGrid(
            cells = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            items(count = bitmap.size + 1) { index ->
                if (index == bitmap.size) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_add_a_photo_24),
                        contentDescription = "Добавить фотографию",
                        modifier = Modifier.clickable {
                            launcher.launch("image/*")
                        }.width(90.dp).height(90.dp).padding(top = ((128f - 90f) / 2f).dp)
                    )
                } else {
                    Image(
                        bitmap = bitmap[index].asImageBitmap(),
                        contentDescription = "Фото/скан схемы",
                        modifier = Modifier
                            .size(128.dp)
                            .clickable {
                                removeImageIndex = 0
                            },
                    )
                }
            }
        }

        if (removeImageIndex != null) {
            AlertDialog(
                text = {
                    Text(text = "Вы действительно хотите убрать это изображение из списка?")
                },

                onDismissRequest = { removeImageIndex = null },

                confirmButton = {
                    Text(
                        color = Color(0xFF018786),
                        text = "Убрать",
                        modifier = Modifier
                            .clickable {
                                bitmap.removeAt(removeImageIndex!!)
                                viewModel.photos.removeAt(removeImageIndex!!)
                                removeImageIndex = null
                            }
                            .padding(10.dp)
                    )
                },

                dismissButton = {
                    Text(
                        color = Color(0xFF018786),
                        text = "Оставить",
                        modifier = Modifier
                            .clickable {
                                removeImageIndex = null
                            }
                            .padding(10.dp)
                    )
                },

                title = {
                    Text(text = "Убрать изображение")
                },
            )
        }
    }
}

@Composable
@Preview
fun CreatingNew2ContentPreview() {
    CreatingNew2Content(ClientInfoViewModel())
}

