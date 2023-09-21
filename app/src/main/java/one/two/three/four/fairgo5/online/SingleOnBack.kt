package one.two.three.four.fairgo5.online

import android.webkit.WebView
import androidx.activity.OnBackPressedCallback

class SingleOnBack(private val singleView: WebView, enabled: Boolean): OnBackPressedCallback(enabled) {
    override fun handleOnBackPressed() {
        singleView.ifCanGoBack()
    }

    private fun WebView.ifCanGoBack() {
        if(canGoBack()) {
            goBack()
        }
    }
}