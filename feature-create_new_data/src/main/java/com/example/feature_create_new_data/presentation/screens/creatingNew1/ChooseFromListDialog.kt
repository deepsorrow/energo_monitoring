package com.example.feature_create_new_data.presentation.screens.creatingNew1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_create_new_data.data.ClientInfo

@Composable
fun ChooseFromListDialog(
    title: String,
    list: List<ClientInfo>,
    openDialog: Boolean,
    onCloseClicked: () -> Unit,
    onConfirmClicked: (Int) -> Unit
) {
    if (openDialog) {
        var searchQuery by remember {
            mutableStateOf("")
        }

        AlertDialog(
            onDismissRequest = onCloseClicked,
            title = { },
            text = {
                Column {
                    OutlinedTextField(
                        modifier = Modifier.padding(bottom = 5.dp),
                        value = searchQuery,
                        placeholder = {
                            Row() {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search"
                                )
                                Text(text = "Поиск")
                            }
                        },
                        onValueChange = { searchQuery = it },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
                    )
                    LazyColumn(
                        modifier = Modifier.border(
                            width = 2.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(10.dp)
                        )
                    )
                    {
                        items(items = list) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = if (item != list[list.size - 1]) 5.dp else 0.dp)
                                    .clickable { onConfirmClicked(item.agreementNumber) },
                                border = BorderStroke(0.1.dp, Color.Gray)
                            ) {
                                Text(
                                    text = "${item.agreementNumber}\n${item.name}",
                                    modifier = Modifier.padding(5.dp),
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
}

@Composable
@Preview
fun PreviewDialog() {
    ChooseFromListDialog(
        title = "Выберите договор абонента",
        list = listOf(
            ClientInfo(0, 123120321, "Школа №3", "", "", "", "", false),
            ClientInfo(1, 123131321, "Школа №551", "", "", "", "", false),
            ClientInfo(2, 155120321, "Школа №321", "", "", "", "", false)
        ),
        openDialog = true,
        onCloseClicked = {},
        onConfirmClicked = {}
    )
}