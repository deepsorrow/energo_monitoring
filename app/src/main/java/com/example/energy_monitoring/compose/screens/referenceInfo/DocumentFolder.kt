package com.example.energy_monitoring.compose.screens.referenceInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energy_monitoring.R
import com.example.energy_monitoring.compose.data.api.RefDoc
import com.example.energy_monitoring.compose.viewmodels.RefDocsVM

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DocumentFolder(folder: RefDoc,
                   onClick: () -> Unit,
                   viewModel: RefDocsVM = hiltViewModel()){
    Row(modifier = Modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_folder),
                contentDescription = "Папка " + folder.title
            )
            Column(modifier = Modifier.padding(start = 5.dp)) {
                Text(
                    text = folder.title,
                )
                Text(
                    text = viewModel.getFilesInsideText(folder),
                    color = Color(184, 184, 184, 235)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewFolder(){
    DocumentFolder(RefDoc("Водосчетчики"), {})
}