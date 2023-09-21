@file:Suppress("SameParameterValue")

package one.two.three.four.fairgo5.online

import android.Manifest
import android.net.Uri
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
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val uri = request.url.toString()
            return if (uri.contains("/")) {
                Log.e("Uri", uri)
                return !uri.contains("http")
            } else true
        }
    }
    
    inner class SingleWebChromeClient(
        private val requestPermissionLauncher: ActivityResultLauncher<String>
    ) : WebChromeClient() {

        override fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            singleFPCallback = filePathCallback
            return true
        }
    }
}