package com.ssslotssfair.gopokiess.useful

import android.content.Context
import android.content.Intent
import com.ssslotssfair.gopokiess.OfflineGameImportant
import com.ssslotssfair.gopokiess.OnlineGameImportant

object ActivityIntentCreator {
    fun online(context: Context) = Intent(context, OnlineGameImportant::class.java)
    fun offline(context: Context) = Intent(context, OfflineGameImportant::class.java)
}