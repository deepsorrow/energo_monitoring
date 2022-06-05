package com.example.energy_monitoring.compose.screens.referenceInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetListItem(item: BottomSheetActions, onItemClick: (BottomSheetActions) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(55.dp)
            .background(color = Color(247, 247, 247, 206))
            .padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = item.icon), contentDescription = "Share", tint = Color.Black)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = item.title, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetListItemPreview() {
    BottomSheetListItem(item = BottomSheetActions.ADD_FILE, onItemClick = { })
}