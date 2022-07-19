package com.example.energy_monitoring.compose.screens.creatingNew1

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energy_monitoring.R
import com.example.energy_monitoring.compose.ContractInfo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AgreementListCard(agreement: ContractInfo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 8.dp, end = 8.dp, top = 8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(0.0.dp, Color.Gray, CircleShape),
                painter = painterResource(id = R.drawable.school),
                contentDescription = "Фото школы"
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = agreement.agreementNumber, fontWeight = FontWeight.W600, fontSize = 16.5.sp)
                Text(text = agreement.name, fontSize = 16.sp)
                Text(text = agreement.addressUUTE, fontSize = 16.sp)
            }
        }
    }
}