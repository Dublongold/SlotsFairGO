package com.ssslotssfair.gopokiess.useful

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetOutdoorData {

    fun initializeOneSignal(context: Context, oneSignalId: String) {
        OneSignal.initWithContext(context, oneSignalId)
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)
        }
    }

    fun getDataFromFirebase(context: Context, callback: (String?) -> Unit) {
        FirebaseApp.initializeApp(context)

        Firebase.remoteConfig.reset().addOnCompleteListener {
            Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {
                val allow = Firebase.remoteConfig.getBoolean("permission")
                val url = Firebase.remoteConfig.getString("reference")
                callback(if(allow && url.isNotEmpty()) url else null)
            }
        }
    }
}