package com.bryanollivie.resume

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url) ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}

actual fun shareText(text: String) {
    val controller = platform.UIKit.UIActivityViewController(
        activityItems = listOf(text),
        applicationActivities = null
    )
    UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
        controller, animated = true, completion = null
    )
}
