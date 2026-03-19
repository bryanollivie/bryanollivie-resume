package com.bryanollivie.resume.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bryanollivie.resume.designsystem.tokens.ResumeColors
import com.bryanollivie.resume.designsystem.tokens.ResumeShapes

@Composable
fun DateBadge(
    text: String,
    color: Color = ResumeColors.Primary,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(ResumeShapes.Medium)
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}
