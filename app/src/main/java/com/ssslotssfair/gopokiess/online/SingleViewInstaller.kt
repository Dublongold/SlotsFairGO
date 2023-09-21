@file:Suppress("SameParameterValue")

package com.ssslotssfair.gopokiess.online

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Message
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher

class SingleViewInstaller(
    private val singleView: WebView,
    val link: String
) {
    var singleFPCallback: ValueCallback<Array<Uri>>? = null

    var mixedContentMode: Int = 0
    var cacheMode: Int = 0

    private val states = arrayOf(
        false, false, false
    )

    var booleansValue = false

    @Suppress("DEPRECATION")
    fun installSettings() {
        singleView.settings.allowContentAccess = booleansValue
        singleView.settings.allowFileAccess = booleansValue
        singleView.settings.javaScriptCanOpenWindowsAutomatically = booleansValue
        singleView.settings.allowFileAccessFromFileURLs = booleansValue
        singleView.settings.domStorageEnabled = booleansValue
        singleView.settings.javaScriptEnabled = booleansValue
        singleView.settings.databaseEnabled = booleansValue
        singleView.settings.allowUniversalAccessFromFileURLs = booleansValue
        singleView.settings.useWideViewPort = booleansValue
        singleView.settings.loadWithOverviewMode = booleansValue
        installIntegers()
        installUserAgent("; wv")
        states[0] = true
    }

    private fun installIntegers() {
        singleView.settings.mixedContentMode = mixedContentMode
        singleView.settings.cacheMode = cacheMode
    }

    private fun installUserAgent(string: String) {
        val usrAgent = singleView.settings.userAgentString
        singleView.settings.userAgentString = usrAgent.replace(string, "")
    }
    
    fun installCookies() {
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(singleView, true)
        states[1] = true
    }
    
    fun installClients(requestPermissionLauncher: ActivityResultLauncher<String>) {
        singleView.webViewClient = SingleWebClient()
        singleView.webChromeClient = SingleWebChromeClient(requestPermissionLauncher)
        states[2] = true
    }
    
    fun finishInstallation() {
        if(states.all { it }) {
            singleView.loadUrl(link)
        }
        else {
            throw IllegalStateException("Installation is not over.")
        }
    }

    inner class SingleWebClient : WebViewClient() {
        private var pageState = -1


        private val pageStarted = 1
        private val pageCommitVisible = 1
        private val pageFinished = 3
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val uri = request.url.toString()
            return if (uri.contains("/")) {
                Log.e("Uri", uri)
                return !uri.contains("http")
            } else true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            pageState = pageStarted
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            pageState = pageFinished
        }

        override fun onPageCommitVisible(view: WebView?, url: String?) {
            super.onPageCommitVisible(view, url)
            pageState = pageCommitVisible
        }

    }
    
    inner class SingleWebChromeClient(
        private val requestPermissionLauncher: ActivityResultLauncher<String>
    ) : WebChromeClient() {
        private var windowState: Boolean = false
        override fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            singleFPCallback = filePathCallback
            return true
        }

        override fun onCloseWindow(window: WebView?) {
            super.onCloseWindow(window)
            windowState = false
        }

        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            windowState = true
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
        }
    }
}