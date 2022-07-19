package com.example.energy_monitoring.compose.screens.creatingNew1

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.energy_monitoring.R
import com.example.energy_monitoring.compose.ContractInfo
import com.example.energy_monitoring.compose.screens.FloatingPlusButton
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AgreementNumTextField(
    placeholder: String,
    agreementNumber: String,
    available: List<ContractInfo>,
    onAgreementNumberChanged: (String) -> Unit,
    onContractSelected: (ContractInfo) -> Unit,
) {
    val context = LocalContext.current
    var openDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = placeholder)
            },
            value = if (agreementNumber == "0") "" else agreementNumber,
            onValueChange = {
                onAgreementNumberChanged(agreementNumber)
            },
        )
        Button(
            modifier = Modifier.height(56.dp).padding(top = 7.dp, end = 2.dp),
            onClick = { openDialog = true },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(248, 248, 248, 255)
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                "Выбрать договор",
                tint = Color.DarkGray
            )
        }
    }

    if (openDialog) {
        Dialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = { openDialog = false }
        ) {
            Surface(
                modifier = Modifier.padding(10.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(247, 247, 247, 206))
                            .padding(top = 8.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Выбор договора",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Column {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
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
                            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                                items(available) { agreement ->
                                    if (searchQuery.isEmpty() || agreement.name.lowercase(Locale.ROOT)
                                            .contains(searchQuery)
                                        || agreement.addressUUTE.lowercase(Locale.ROOT)
                                            .contains(searchQuery)
                                        || agreement.agreementNumber.lowercase(Locale.ROOT)
                                            .contains(searchQuery)
                                    ) {
                                        AgreementListCard(agreement) {
                                            onContractSelected(agreement)
                                            openDialog = false
                                        }
                                    }
                                }
                            }
                        }
                        FloatingPlusButton(Modifier.padding(end = 25.dp, bottom = 25.dp)) {
                            Toast.makeText(context, "Функционал в разработке", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewAgreementNumTextField() {
    //AgreementNumTextField(placeholder = "Номер договора абонента", null, listOf()) {}
}
