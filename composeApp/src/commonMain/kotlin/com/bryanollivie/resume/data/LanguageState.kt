package com.bryanollivie.resume.data

import androidx.compose.runtime.*

val LocalLanguage = compositionLocalOf { mutableStateOf(AppLanguage.EN) }

@Composable
fun currentStrings(): AppStrings {
    val lang = LocalLanguage.current.value
    return Strings.get(lang)
}
