package com.example.energy_monitoring.checks.ui.utils

import com.example.energy_monitoring.checks.data.db.ResultDataDatabase
import com.example.energy_monitoring.checks.data.db.OtherInfo
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import kotlin.math.min

object Utils {
    /**
     * Записать прогресс для восстанавления из главной страницы при необходимости
     * @param dataId - идентификатор данных для отправки на сервер
     */
    fun logProgress(context: Context?, currentScreen: Int, dataId: Int) {
        val db = ResultDataDatabase.getDatabase(context)
        var otherInfo = db.resultDataDAO().getOtherInfo(dataId)
        if (otherInfo == null) {
            otherInfo = OtherInfo(dataId)
        }
        otherInfo.completedScreens = otherInfo.completedScreens.coerceAtLeast(currentScreen)
        otherInfo.currentScreen = currentScreen
        db.resultDataDAO().insertOtherInfo(otherInfo)
    }

    fun getDataIdFolder(context: Context, dataId: Int) = File(context.filesDir, "$dataId").also { it.mkdirs() }

    fun getRefDocsFolder(context: Context) = File(context.filesDir, "refDocs").also { it.mkdirs() }

    fun getRealPathFromUri(context: Context, uri: Uri, saveFolder: File): String? {
        try {
            val filePathColumn = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
            )

            val returnCursor = context.contentResolver.query(uri, filePathColumn, null, null, null)

            if (returnCursor != null) {
                returnCursor.moveToFirst()
                val nameIndex = returnCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                val name = returnCursor.getString(nameIndex)
                val file = File(saveFolder, name)
                val inputStream = context.contentResolver.openInputStream(uri)
                val outputStream = FileOutputStream(file)
                var read: Int
                val maxBufferSize = 1 * 1024 * 1024
                val bytesAvailable = inputStream!!.available()

                val bufferSize = min(bytesAvailable, maxBufferSize)
                val buffers = ByteArray(bufferSize)

                while (inputStream.read(buffers).also { read = it } != -1) {
                    outputStream.write(buffers, 0, read)
                }

                inputStream.close()
                outputStream.close()
                returnCursor.close()
                return file.absolutePath
            } else {
                return null
            }
        } catch (e: Exception) {
            return null
        }
    }

    fun getStringSizeLengthFile(size: Long): String {
        val df = DecimalFormat("0.00")
        val sizeKb = 1024.0f
        val sizeMb = sizeKb * sizeKb
        val sizeGb = sizeMb * sizeKb
        val sizeTerra = sizeGb * sizeKb
        if (size < sizeMb)
            return df.format(size / sizeKb).toString() + " Кб"
        else if (size < sizeGb)
            return df.format(size / sizeMb).toString() + " Мб"
        else if (size < sizeTerra)
            return df.format(size / sizeGb).toString() + " Гб"

        return ""
    }
}