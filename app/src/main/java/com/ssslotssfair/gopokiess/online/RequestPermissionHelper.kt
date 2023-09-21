package com.ssslotssfair.gopokiess.online

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class RequestPermissionHelper() {

    private var tempFile: File? = null
    var uriCallback: Uri? = null
    fun checkIfGranted(isGranted: Boolean?) {
        Log.i(LOG_TAG, "Is ${if(isGranted == true) "not " else ""} granted.")
    }

    fun createTempFile(externalFilesDir: File?, callback: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                tempFile = try {
                    File.createTempFile(
                        "file",
                        ".jpg",
                        externalFilesDir
                    )
                }
                catch (ex: IOException) {
                    Log.e("PhotoFile", "Unable to cre", ex)
                    null
                }
                callback()
            }
        }
    }

    fun createTakeFileIntent(): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(
                MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(tempFile)
            )
        }
    }

    fun setUriCallbackValue() {
        uriCallback = Uri.fromFile(tempFile)
    }

    fun createPastIntent(cat: String): Intent {
        return Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(cat)
            type = "*/*"
        }
    }

    fun createSelectIntent(past: Intent, takeTempFile: Array<Intent>): Intent {
        return Intent(Intent.ACTION_CHOOSER).apply {

            putExtra(Intent.EXTRA_INTENT, past)
            putExtra(Intent.EXTRA_INITIAL_INTENTS, takeTempFile)
        }
    }

    companion object {
        const val LOG_TAG = "Request permission helper"
    }
}