package one.two.three.four.fairgo5.useful

import android.content.Context
import android.content.Intent
import one.two.three.four.fairgo5.OfflineGameImportant
import one.two.three.four.fairgo5.OnlineGameImportant

object ActivityIntentCreator {
    fun online(context: Context) = Intent(context, OnlineGameImportant::class.java)
    fun offline(context: Context) = Intent(context, OfflineGameImportant::class.java)
}