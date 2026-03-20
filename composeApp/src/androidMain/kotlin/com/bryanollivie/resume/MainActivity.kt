package com.bryanollivie.resume

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bryanollivie.kmputils.KMPUtilsContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidContext.init(application)
        KMPUtilsContext.init(application)
        setContent {
            App()
        }
    }
}
