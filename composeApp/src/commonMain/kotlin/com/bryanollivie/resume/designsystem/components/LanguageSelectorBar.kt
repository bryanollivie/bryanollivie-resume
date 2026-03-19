package com.bryanollivie.resume.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bryanollivie.resume.data.AppLanguage
import com.bryanollivie.resume.designsystem.tokens.ResumeColors
import com.bryanollivie.resume.designsystem.tokens.ResumeShapes

@Composable
fun LanguageSelectorBar(
    currentLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LanguageButton(
            label = "PT",
            isSelected = currentLanguage == AppLanguage.PT,
            onClick = { onLanguageSelected(AppLanguage.PT) }
        )
        LanguageButton(
            label = "EN",
            isSelected = currentLanguage == AppLanguage.EN,
            onClick = { onLanguageSelected(AppLanguage.EN) }
        )
    }
}

@Composable
private fun LanguageButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(ResumeShapes.Small)
            .background(
                if (isSelected) ResumeColors.Primary
                else ResumeColors.White.copy(alpha = 0.15f)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = ResumeColors.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}
