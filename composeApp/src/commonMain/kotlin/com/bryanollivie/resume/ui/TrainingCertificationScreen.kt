package com.bryanollivie.resume.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bryanollivie.resume.data.*
import com.bryanollivie.resume.designsystem.components.*
import com.bryanollivie.resume.designsystem.tokens.ResumeColors
import com.bryanollivie.resume.designsystem.tokens.Spacing

@Composable
fun TrainingCertificationScreen() {
    val strings = currentStrings()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Spacing.dp16),
        verticalArrangement = Arrangement.spacedBy(Spacing.dp16)
    ) {
        item {
            ScreenTitle(text = strings.trainingCertifications)
        }

        items(strings.certificationList) { cert ->
            val isInProgress = cert.name.contains("In Progress") || cert.name.contains("Em Andamento")
            val badgeColor = if (isInProgress) ResumeColors.Accent else ResumeColors.Primary

            SectionCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DateBadge(
                        text = cert.year,
                        color = badgeColor
                    )

                    Spacer(modifier = Modifier.width(Spacing.dp16))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = cert.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(Spacing.dp4))
                        Text(
                            text = cert.institution,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    if (isInProgress) {
                        TagChip(
                            text = strings.inProgress,
                            backgroundColor = ResumeColors.Accent.copy(alpha = 0.15f),
                            textColor = ResumeColors.Accent,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
