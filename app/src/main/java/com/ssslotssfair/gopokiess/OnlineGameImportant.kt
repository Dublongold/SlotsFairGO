package com.ssslotssfair.gopokiess

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.ssslotssfair.gopokiess.online.OnActivityResultHelper
import com.ssslotssfair.gopokiess.online.RequestPermissionHelper
import com.ssslotssfair.gopokiess.online.SingleOnBack
import com.ssslotssfair.gopokiess.online.SingleViewInstaller


class OnlineGameImportant : AppCompatActivity() {
    private lateinit var singleViewInstaller: SingleViewInstaller
    private lateinit var singleView: WebView
    private lateinit var requestPermissionHelper: RequestPermissionHelper
    private var singleFPCallback
        get() = singleViewInstaller.singleFPCallback
        set(value) {
            singleViewInstaller.singleFPCallback = value
        }
    private var uriCallback
        get() = requestPermissionHelper.uriCallback
        set(value) {
            requestPermissionHelper.uriCallback = value
        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.important_online)
        singleView = findViewById(R.id.singleView)
        onBackPressedDispatcher.addCallback(SingleOnBack(singleView, true))
        requestPermissionHelper = RequestPermissionHelper()
        singleViewInstaller = SingleViewInstaller(singleView, Firebase.remoteConfig.getString("reference"))
        startInstallation()
    }

    private fun startInstallation() {
        singleViewInstaller.booleansValue = true
        singleViewInstaller.cacheMode = WebSettings.LOAD_DEFAULT
        singleViewInstaller.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        singleViewInstaller.installCookies()
        singleViewInstaller.installSettings()
        singleViewInstaller.installClients(requestPermissionLauncher)
        singleViewInstaller.finishInstallation()
    }

    private val requestPermissionLauncher = registerForActivityResult (
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean? ->
        requestPermissionHelper.checkIfGranted(isGranted)

        requestPermissionHelper.createTempFile(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)) {
            val takeTempFileAsArray = Array(1){(requestPermissionHelper.createTakeFileIntent())}
            requestPermissionHelper.setUriCallbackValue()
            //
            val past = requestPermissionHelper.createPastIntent(Intent.CATEGORY_OPENABLE)
            //
            val select = requestPermissionHelper.createSelectIntent(past, takeTempFileAsArray)
            startActivityForResult(select, FOR_RESULT_REQUEST_CODE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val onActivityResultHelper = OnActivityResultHelper(singleFPCallback, uriCallback)
        if(onActivityResultHelper.checkCallback()) {
            onActivityResultHelper.doIfResultCodeIsOK(resultCode == REQUEST_CODE_ALLOWED, data)
            singleFPCallback = null
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        singleView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        singleView.restoreState(savedInstanceState)
    }

    companion object {
        const val REQUEST_CODE_ALLOWED = -1
        const val FOR_RESULT_REQUEST_CODE = 1
    }
}