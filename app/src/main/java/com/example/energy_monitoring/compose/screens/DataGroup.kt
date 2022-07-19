package com.example.energy_monitoring.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energy_monitoring.R
import com.example.energy_monitoring.compose.domain.DropDownMenu
import com.example.energy_monitoring.compose.screens.creatingNew3.DropDownDeviceActions

@Composable
fun DataGroup(
    title: String,
    items: List<DropDownMenu> = listOf(),
    onItemClicked: ((DropDownMenu) -> Unit)? = null
) {
    var showMoreExpand by remember { mutableStateOf(false) }

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .heightIn(min = 40.dp)
                    .background(color = Color(238, 238, 238, 134)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = title,
                    fontSize = 20.sp,
                    color = Color.DarkGray
                )

                if (onItemClicked != null) {
                    Box {
                        IconButton(
                            onClick = { showMoreExpand = !showMoreExpand }
                        ) {
                            Icon(
                                modifier = Modifier.defaultMinSize(
                                    minWidth = ButtonDefaults.MinWidth,
                                    minHeight = 0.dp
                                ),
                                painter = painterResource(R.drawable.ic_baseline_more_vert_24),
                                contentDescription = "Дополнительные действия",
                            )
                        }

                        if (showMoreExpand) {
                            DropdownMenu(
                                expanded = showMoreExpand,
                                onDismissRequest = { showMoreExpand = !showMoreExpand },
                            ) {
                                items.forEach { item ->
                                    DropdownMenuItem(
                                        modifier = Modifier.width(230.dp),
                                        onClick = { onItemClicked(item) }
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = item.icon),
                                                tint = Color.DarkGray,
                                                contentDescription = item.title
                                            )
                                            Text(
                                                modifier = Modifier.padding(start = 5.dp),
                                                text = item.title
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
}