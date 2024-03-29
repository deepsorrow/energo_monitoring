package com.example.energy_monitoring.compose.screens.creatingNew1

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CreatingTextField(placeholder: String, value: String, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
                .padding(bottom = 5.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
        singleLine = true,
        label = {
            Text(text = placeholder)
        },
        value = if (/* TODO: а зачем? */ value == "0") "" else value,
        onValueChange = onValueChanged,
    )
}

@Composable
fun CreatingLongTextField(placeholder: String, value: String, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
                .padding(bottom = 5.dp, start = 20.dp, end = 20.dp)
                .height(120.dp)
                .fillMaxWidth(),
        label = {
            Text(text = placeholder)
        },
        value = if (/* TODO: а зачем? */ value == "0") "" else value,
        onValueChange = onValueChanged,
    )
}

@Composable
@Preview
fun CreatingTextFieldPreview() {
    CreatingTextField(placeholder = "Номер договора абонента", "123") {}
}

@Composable
@Preview
fun CreatingLongTextFieldPreview() {
    CreatingLongTextField(placeholder = "Текст", "123\nабв") {}
}
