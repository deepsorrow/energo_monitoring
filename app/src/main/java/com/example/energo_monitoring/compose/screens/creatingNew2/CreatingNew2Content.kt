package com.example.energo_monitoring.compose.screens.creatingNew2

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energo_monitoring.compose.screens.creatingNew1.CreatingLongTextField
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel

@Composable
fun CreatingNew2Content(
    viewModel: ClientInfoViewModel
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 15.dp, start = 20.dp),
            text = "Проверка данных предоставленных закачиком",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp, start = 10.dp)
        ) {
            Checkbox(checked = viewModel.matchesConditions.value, onCheckedChange = {
                viewModel.matchesConditions.value = it
            })

            Text(
                text = "Соответствие проекта нормативным требованиям",
                fontSize = 17.sp
            )
        }

        CreatingLongTextField(placeholder = "Комментарий", viewModel.commentary.value) {
            viewModel.commentary.value = it
        }

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
                        modifier = Modifier.clickable {
                            alertBrokenImage = false
                        }.padding(10.dp)
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

        when (bitmap.size) {
            0 -> {
                Text(
                    text = "Фото/скан схемы не выбран...",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            1 -> {
                Text(
                    text = "Фото/скан схемы:",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                val obj = bitmap[0]

                Image(
                    bitmap = obj.asImageBitmap(),
                    contentDescription = "Фото/скан схемы",
                    modifier = Modifier
                        .size(400.dp)
                        .clickable {
                            removeImageIndex = 0
                        },
                )
            }

            else -> {
                Text(
                    text = "Фото/сканы схемы:",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                for ((i, img) in bitmap.withIndex()) {
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "Фото/скан схемы",
                        modifier = Modifier
                            .size(400.dp)
                            .clickable {
                                removeImageIndex = i
                            },
                    )
                }
            }
        }

        Button(
            onClick = {
                launcher.launch("image/*")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "Выбрать изображение")
        }

        if (removeImageIndex != null) {
            AlertDialog(
                text = {
                    Text(text = "Вы действительно хотите убрать это изображение из списка?")
                },

                onDismissRequest = { removeImageIndex = null },

                confirmButton = {
                    Text(
                        color = Color.Red,
                        text = "Убрать",
                        modifier = Modifier.clickable {
                            bitmap.removeAt(removeImageIndex!!)
                            viewModel.photos.removeAt(removeImageIndex!!)
                            removeImageIndex = null
                        }.padding(10.dp)
                    )
                },

                dismissButton = {
                    Text(
                        color = Color.Cyan,
                        text = "Оставить",
                        modifier = Modifier.clickable {
                            removeImageIndex = null
                        }.padding(10.dp)
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

