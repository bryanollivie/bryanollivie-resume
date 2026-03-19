package com.bryanollivie.resume.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val PrimaryRed = Color(0xFFD32F2F)
val PrimaryDark = Color(0xFF212121)
val SurfaceLight = Color(0xFFFAFAFA)
val OnSurface = Color(0xFF333333)
val SecondaryText = Color(0xFF666666)
val CardBackground = Color(0xFFFFFFFF)
val DividerColor = Color(0xFFE0E0E0)
val AccentGreen = Color(0xFF4CAF50)
val ChipBackground = Color(0xFFF5F5F5)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryRed,
    onPrimary = Color.White,
    secondary = PrimaryDark,
    onSecondary = Color.White,
    background = SurfaceLight,
    surface = CardBackground,
    onBackground = OnSurface,
    onSurface = OnSurface,
    surfaceVariant = ChipBackground,
    outline = DividerColor
)

val ResumeTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        color = PrimaryDark
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        color = PrimaryDark
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        color = PrimaryDark
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        color = PrimaryDark
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = SecondaryText
    ),
    bodyLarge = TextStyle(
        fontSize = 15.sp,
        lineHeight = 22.sp,
        color = OnSurface
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = OnSurface
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = SecondaryText
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)

@Composable
fun ResumeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = ResumeTypography,
        content = content
    )
}
