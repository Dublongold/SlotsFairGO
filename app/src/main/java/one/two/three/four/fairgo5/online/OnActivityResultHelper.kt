package one.two.three.four.fairgo5.online

import android.content.Intent
import android.net.Uri
import android.webkit.ValueCallback

class OnActivityResultHelper(private val singleFPCallback: ValueCallback<Array<Uri>>?,
                             private val uriCallback: Uri?) {
    fun checkCallback(): Boolean {
        return singleFPCallback != null
    }

    fun doIfResultCodeIsOK(isOK: Boolean, data: Intent?) {
        if(singleFPCallback != null) {
            if (isOK) {
                if (checkData(data)) {
                    singleFPCallback.onReceiveValue(arrayOf(Uri.parse(data?.dataString)))
                }
                else {
                    elseOnReceiveValue()
                }
            } else {
                defaultAction()
            }
        }
    }

    private fun checkData(data: Intent?): Boolean {
        return data?.dataString != null
    }

    private fun defaultAction() {
        singleFPCallback?.onReceiveValue(null)
    }

    private fun elseOnReceiveValue() {
        if(singleFPCallback != null) {
            if (uriCallback != null) {
                singleFPCallback.onReceiveValue(arrayOf(uriCallback))
            } else {
                singleFPCallback.onReceiveValue(null)
            }
        }
    }
}