@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.bryanollivie.resume

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIActivityViewController

actual fun openUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url) ?: return
    UIApplication.sharedApplication.openURL(nsUrl, emptyMap<Any?, Any>(), null)
}

actual fun shareText(text: String) {
    val controller = UIActivityViewController(
        activityItems = listOf(text),
        applicationActivities = null
    )
    UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
        controller, animated = true, completion = null
    )
}
