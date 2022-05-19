package com.example.energo_monitoring.compose.screens.referenceInfo

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
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.data.api.RefDoc
import com.example.energo_monitoring.compose.viewmodels.RefDocsVM

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DocumentFile(file: RefDoc,
                 onClick: () -> Unit,
                 viewModel: RefDocsVM = hiltViewModel()) {
    Row(modifier = Modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val extension = file.title.substringAfterLast(".").lowercase()
            val iconId = when (extension) {
                "pdf" -> R.drawable.pdf_96
                "doc", "docx" -> R.drawable.word_96
                "jpg", "png", "bmp" -> R.drawable.image_96
                else -> R.drawable.ic_file
            }
            Image(
                modifier = Modifier.size(45.dp),
                painter = painterResource(id = iconId),
                contentDescription = "Файл " + file.title
            )
            Column(modifier = Modifier.padding(start = 5.dp)) {
                Text(
                    text = file.title,
                )
                Text(
                    text = file.size,
                    color = Color(184, 184, 184, 235)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDocumentFile(){
    DocumentFile(RefDoc("МРТ-1982.pdf"), {})
}