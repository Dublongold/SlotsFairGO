package one.two.three.four.fairgo5.useful

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.remoteconfig.ktx.remoteConfig

class GetOutdoorData {

    fun initializeOneSignal() {
        // TODO
    }

    fun getDataFromFirebase(context: Context, callback: (String?) -> Unit) {
        FirebaseApp.initializeApp(context)

        Firebase.remoteConfig.reset().addOnCompleteListener {
            Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {
                val allow = Firebase.remoteConfig.getBoolean("allow_access")
                val url = Firebase.remoteConfig.getString("link")
                callback(if(allow && url.isNotEmpty()) url else null)
            }
        }
    }
}