package com.bryanollivie.resume

import android.content.Intent
import android.net.Uri
import android.app.Application
import java.lang.ref.WeakReference

object AndroidContext {
    private var appContext: WeakReference<Application>? = null

    fun init(app: Application) {
        appContext = WeakReference(app)
    }

    fun get() = appContext?.get()
}

actual fun openUrl(url: String) {
    val context = AndroidContext.get() ?: return
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}

actual fun shareText(text: String) {
    val context = AndroidContext.get() ?: return
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(Intent.createChooser(intent, null).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}
