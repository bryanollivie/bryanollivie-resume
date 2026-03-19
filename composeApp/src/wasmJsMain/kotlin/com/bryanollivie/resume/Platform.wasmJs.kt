package com.bryanollivie.resume

import kotlinx.browser.window

actual fun openUrl(url: String) {
    window.open(url, "_blank")
}

actual fun shareText(text: String) {
    window.open("https://wa.me/?text=${kotlinx.browser.window.encodeURIComponent(text)}", "_blank")
}
