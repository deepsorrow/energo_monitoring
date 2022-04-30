package com.example.energo_monitoring.compose.screens.creatingNew2

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.energo_monitoring.compose.screens.creatingNew1.CreatingLongTextField
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel

@Composable
fun CreatingNew2Content(
    viewModel: ClientInfoViewModel,
    goToNextScreen: () -> Unit
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

        var bitmap by remember {
            mutableStateOf<Bitmap?>(null)
        }

        val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = {
            viewModel.photo = it
        })

        bitmap = if (viewModel.photo != null) {
            if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, viewModel.photo!!)
            } else {
                val decoder = ImageDecoder.createSource(context.contentResolver, viewModel.photo!!)
                ImageDecoder.decodeBitmap(decoder)
            }
        } else {
            null
        }

        if (bitmap != null) {
            Text(
                text = "Фото/скан схемы:",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = "Фото/скан схемы",
                modifier = Modifier.size(400.dp)
            )
        } else {
            Text(
                text = "Фото/скан схемы не выбран...",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
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

        Button(
            onClick = goToNextScreen,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "Продолжить")
        }
    }
}

@Composable
@Preview
fun CreatingNew2ContentPreview() {
    CreatingNew2Content(ClientInfoViewModel()) {}
}

