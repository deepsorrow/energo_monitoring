package com.example.energo_monitoring.compose.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energo_monitoring.checks.data.api.ServerApi
import com.example.energo_monitoring.compose.data.api.RefDoc
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class RefDocsVM @Inject constructor(
    @ApplicationContext // не утекает, т.к. applicationContext
    private val context: Context,
    private val serverApi: ServerApi
 ) : ViewModel() {
    var refDocs = mutableStateListOf<RefDoc>()
    var currentFile by mutableStateOf(RefDoc())
    var currentChildFiles = mutableStateListOf<RefDoc>()

    fun getRefDocs() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = serverApi.allRefDocsWithoutData.awaitResponse()
            if (response.isSuccessful) {
                refDocs.clear()
                refDocs.addAll(response.body() ?: mutableStateListOf())
                val topLevelFiles = refDocs
                    .filter { it.isFolder || it.parentFolder == "" }
                    .sortedBy { it.title }
                currentChildFiles.addAll(topLevelFiles)
            }
        } catch (t: Throwable) {
            //Toast.makeText(context, "Код ошибки: 102. $t", Toast.LENGTH_LONG).show()
        }
    }

    fun getFilesInsideText(folder: RefDoc): String {
        val filesCount = refDocs.filter { it.parentFolder == folder.title }.count()
        return when (filesCount % 10) {
            1 -> "$filesCount файл"
            2, 3, 4 -> "$filesCount файла"
            else -> "$filesCount файлов"
        }
    }

    fun getCurrentFilesForFile(file: RefDoc) {
        updateChildFiles(refDocs.filter { it.parentFolder == file.title }.sortedBy { it.title })
        currentFile = file
    }

    private fun getTopLevelFiles() = refDocs
            .filter { it.isFolder || it.parentFolder == "" }
            .sortedBy { it.title }

    fun navigateBack() {
        if (currentFile.isEmpty())
            return

        if (currentFile.parentFolder == "") {
            updateChildFiles(getTopLevelFiles())
            currentFile = RefDoc()
            return
        }

        val parent = refDocs.firstOrNull { it.isFolder && it.title == currentFile.parentFolder }
        if (parent != null) {
            updateChildFiles(refDocs.filter { it.parentFolder == parent.title }.sortedBy { it.title })
            currentFile = parent
        } else {
            Toast.makeText(context,
                "Не удалось найти родителя ${currentFile.parentFolder} у файла" +
                        " ${currentFile.title}", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateChildFiles(newFiles: List<RefDoc>) {
        currentChildFiles.clear()
        currentChildFiles.addAll(newFiles)
    }
}