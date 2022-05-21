package com.example.energo_monitoring.compose.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energo_monitoring.checks.data.api.ServerApi
import com.example.energo_monitoring.compose.data.api.RefDoc
import com.example.energo_monitoring.compose.domain.RefDocRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


@SuppressLint("StaticFieldLeak")
@HiltViewModel
class RefDocsVM @Inject constructor(
    @ApplicationContext // не утекает, т.к. applicationContext
    private val context: Context,
    private val serverApi: ServerApi,
    private val repository: RefDocRepository
 ) : ViewModel() {
    var isLoading by mutableStateOf(false)
    var refDocs = mutableStateListOf<RefDoc>()
    var currentFile by mutableStateOf(RefDoc())
    var currentChildFiles = mutableStateListOf<RefDoc>()

    fun getRefDocs() = viewModelScope.launch(Dispatchers.IO) {
        try {
            isLoading = true
            val response = serverApi.allRefDocsWithoutData().awaitResponse()
            if (response.isSuccessful) {
                refDocs.clear()
                response.body()?.forEach { refDocs.add(it) }
                val topLevelFiles = refDocs
                    .filter { it.isFolder || it.parentFolder == "" }
                    .sortedBy { it.title }
                currentChildFiles.addAll(topLevelFiles)
                isLoading = false
            }
        } catch (t: Throwable) {
            //Toast.makeText(context, "Код ошибки: 102. $t", Toast.LENGTH_LONG).show()
        }
    }

    fun getAndOpenFile(refDoc: RefDoc) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TAG", "Execution thread: "+Thread.currentThread().name)
        val fileToOpen = async {
            try {
                Log.d("TAG", "Execution thread1: "+Thread.currentThread().name)
                isLoading = true
                val response = serverApi.getRefDocById(refDoc.id).awaitResponse()
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
//                        val extension = refDoc.title.substringAfterLast(".").lowercase()
//                        val file = File.createTempFile("refDoc", ".$extension", context.filesDir)
//                        val os = FileOutputStream(file)
//                        os.write(result)
//                        os.close()
//
//                        refDoc.localFilePath = file.canonicalPath
//                        repository.insertRefDoc(refDoc)

                        return@async refDoc
                    }
                }
                return@async null

            } catch (t: Throwable) {
                return@async null
            }
        }.await()

        if (fileToOpen != null) {
            openFile(fileToOpen)
        }
    }

    private fun openFile(refDoc: RefDoc) {
        val file = File(refDoc.localFilePath)
        if (file.exists()) {
            val uri: Uri = Uri.fromFile(file)
            val intent = Intent(Intent.ACTION_VIEW)

            val extension = refDoc.title.substringAfterLast(".").lowercase()
            when (extension) {
                "doc", "docx" -> intent.setDataAndType(uri, "application/msword")
                "pdf" -> intent.setDataAndType(uri, "application/pdf")
                "jpeg", "jpg" -> intent.setDataAndType(uri, "image/jpeg")
                "txt" -> intent.setDataAndType(uri, "text/plain")
                "3gp", "mpg", "mp4", "avi" -> intent.setDataAndType(uri, "video/*")
                else -> intent.setDataAndType(uri, "*/*")
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Не удалось открыть файл! Код ошибки: 181.", Toast.LENGTH_LONG).show()
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

    fun navigateBack(): Boolean {
        if (currentFile.isEmpty())
            return false

        if (currentFile.parentFolder == "") {
            updateChildFiles(getTopLevelFiles())
            currentFile = RefDoc()
            return true
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
        return true
    }

    private fun updateChildFiles(newFiles: List<RefDoc>) {
        currentChildFiles.clear()
        currentChildFiles.addAll(newFiles)
    }
}