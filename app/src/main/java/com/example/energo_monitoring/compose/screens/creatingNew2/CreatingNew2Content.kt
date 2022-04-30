package com.example.energo_monitoring.compose.screens.creatingNew2

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energo_monitoring.compose.screens.creatingNew1.CreatingLongTextField
import com.example.energo_monitoring.compose.screens.creatingNew1.CreatingTextField
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel

@Composable
fun CreatingNew2Content(
    viewModel: ClientInfoViewModel,
    goToNextScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 15.dp, start = 20.dp),
            text = "Проверка данных предоставленных закачиком",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp, start = 10.dp)
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

