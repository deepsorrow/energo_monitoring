package com.example.energy_monitoring.compose.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energy_monitoring.checks.data.api.ServerApi
import com.example.energy_monitoring.checks.ui.utils.Utils
import com.example.energy_monitoring.compose.data.api.RefDoc
import com.example.energy_monitoring.compose.domain.RefDocRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.math.min
import kotlin.random.Random


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
    var currentFolder by mutableStateOf(RefDoc())
    var currentChildFiles = mutableStateListOf<RefDoc>()

    var newFolderName by mutableStateOf("")

    fun getRefDocs() = viewModelScope.launch(Dispatchers.IO) {
        try {
            isLoading = true
            val response = serverApi.allRefDocsWithoutData().awaitResponse()
            if (response.isSuccessful) {
                refDocs.clear()
                response.body()?.forEach { refDocs.add(it) }
                updateChildFiles(getTopLevelFiles())
                isLoading = false
            }
        } catch (t: Throwable) {
            //Toast.makeText(context, "Код ошибки: 102. $t", Toast.LENGTH_LONG).show()
        }
    }

    fun getCurrentDocuments() {
        val downloadedRefDocs = repository.getAllRefDocs()
        if (downloadedRefDocs != null) {
            refDocs.clear()
            refDocs.addAll(downloadedRefDocs)
            updateChildFiles(getTopLevelFiles())
        }
    }

    fun downloadFile(refDoc: RefDoc, onComplete: () -> Unit) = viewModelScope.launch {
        val resultFile = async(Dispatchers.IO) {
            try {
                isLoading = true
                val response = serverApi.getRefDocById(refDoc.id).awaitResponse()
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.dataString != null) {
                        val extension = refDoc.title.substringAfterLast(".").lowercase()
                        val folder = Utils.getRefDocsFolder(context)
                        val file = File.createTempFile("refDoc", ".$extension", folder)
                        val os = FileOutputStream(file)
                        os.write(Base64.decode(result.dataString!!, 0))
                        os.close()

                        refDoc.localFilePath = file.canonicalPath
                        repository.insertRefDoc(refDoc)

                        // Пока не в корне, добавляем папки
                        var newFolder: RefDoc? = refDoc
                        while (newFolder != null && newFolder.parentId != 0) {
                            newFolder = refDocs.find { newFolder!!.parentId == it.id }
                            if (newFolder != null && repository.getRefDoc(newFolder.id) == null) {
                                repository.insertRefDoc(newFolder)
                            }
                        }

                        onComplete()
                        return@async Result.success(refDoc)
                    }
                }
                return@async Result.failure(UnknownError("Не удалось получить файл. Код ошибки 817."))

            } catch (t: Throwable) {
                return@async Result.failure(UnknownError("Не удалось получить файл. Код ошибки 818. $t"))
            } finally {
                isLoading = false
            }
        }.await()

        if (resultFile.isFailure) {
            Toast.makeText(context, resultFile.exceptionOrNull().toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun openFile(refDoc: RefDoc) {
        val file = File(refDoc.localFilePath.orEmpty())
        if (file.exists()) {
            val uri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
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
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Не удалось открыть файл! Код ошибки: 181.", Toast.LENGTH_LONG).show()
        }
    }

    fun getFilesInsideText(folder: RefDoc): String {
        val filesCount = refDocs.filter { it.parentId == folder.id }.count()
        return when (filesCount % 10) {
            1 -> "$filesCount файл"
            2, 3, 4 -> "$filesCount файла"
            else -> "$filesCount файлов"
        }
    }

    fun getCurrentFilesForFile(file: RefDoc) {
        updateChildFiles(refDocs.filter { it.parentId == file.id }.sortedBy { it.title })
        currentFolder = file
    }

    fun navigateBack(): Boolean {
        if (currentFolder.isEmpty())
            return false

        if (currentFolder.parentId == 0) {
            updateChildFiles(getTopLevelFiles())
            currentFolder = RefDoc()
            return true
        }

        val parent = refDocs.firstOrNull { it.isFolder && it.id == currentFolder.parentId }
        if (parent != null) {
            updateChildFiles(refDocs.filter { it.parentFolderName == parent.title }.sortedBy { it.title })
            currentFolder = parent
        } else {
            Toast.makeText(context,
                "Не удалось найти родителя ${currentFolder.parentFolderName} у файла" +
                        " ${currentFolder.title}", Toast.LENGTH_LONG).show()
        }
        return true
    }

    fun createNewFolder() {
        val refDoc = RefDoc(newFolderName, true)
        refDoc.id = Random.nextInt(1, 1000000)
        refDoc.parentId = currentFolder.id
        refDoc.parentFolderName = currentFolder.title
        repository.insertRefDoc(refDoc)
        getCurrentDocuments()
        getCurrentFilesForFile(currentFolder)
    }

    fun createNewFile(path: String?) {
        if (path == null)
            return
        val refDoc = RefDoc()
        refDoc.id = Random.nextInt(1, 1000000)
        refDoc.isFolder = false
        refDoc.parentId = currentFolder.id
        refDoc.parentFolderName = currentFolder.title
        refDoc.size = Utils.getStringSizeLengthFile(File(path).length())
        refDoc.title = path.substring(path.lastIndexOf("/") + 1)
        refDoc.localFilePath = path
        repository.insertRefDoc(refDoc)
        getCurrentDocuments()
        getCurrentFilesForFile(currentFolder)
    }

    fun deleteAllDocs(context: Context) {
        if (Utils.getRefDocsFolder(context).deleteRecursively()) {
            repository.deleteRefDocs()
            getCurrentDocuments()
        } else {
            Toast.makeText(context, "Не удалось удалить файлы. Код ошибки: 33", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getTopLevelFiles() = refDocs.filter { it.parentId == 0 }.sortedBy { it.title }

    private fun updateChildFiles(newFiles: List<RefDoc>) {
        currentChildFiles.clear()
        currentChildFiles.addAll(newFiles)
    }
}