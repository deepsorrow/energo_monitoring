package com.example.energy_monitoring.compose.screens.creatingNew1

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energy_monitoring.compose.ContractInfo

@Composable
fun <T : IListEntry> ChooseFromListDialog(
    title: String,
    list: List<T>,
    openDialog: Boolean,
    showSearch: Boolean,
    onCloseClicked: () -> Unit,
    onConfirmClicked: (T) -> Unit
) {
    if (openDialog) {
        var searchQuery by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = onCloseClicked,
            title = {
                if (title.isNotEmpty()) {
                    Text(modifier = Modifier.fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium)
                }
            },
            text = {
                Column {
                    if (showSearch) {
                        OutlinedTextField(
                            modifier = Modifier.padding(bottom = 5.dp),
                            value = searchQuery,
                            placeholder = {
                                Row {
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
                    }
                    LazyColumn {
                        items(items = list.filter { searchQuery.isEmpty() || it.listLabel.contains(searchQuery) }) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 45.dp)
                                    .padding(bottom = if (item != list[list.size - 1]) 5.dp else 0.dp)
                                    .clickable { onConfirmClicked(item) }
                                    .border(0.4.dp, Color.DarkGray)
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxSize().padding(5.dp),
                                    text = item.listLabel,
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
            ContractInfo(0, "123120321", "Школа №3", "", "", "", "", false),
            ContractInfo(1, "123131321", "Школа №551", "", "", "", "", false),
            ContractInfo(2, "155120321", "Школа №321", "", "", "", "", false)
        ),
        openDialog = true,
        false,
        onCloseClicked = {},
        onConfirmClicked = {}
    )
}