package com.bryanollivie.resume.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bryanollivie.resume.data.*
import com.bryanollivie.resume.designsystem.components.*
import com.bryanollivie.resume.designsystem.tokens.ResumeColors
import com.bryanollivie.resume.designsystem.tokens.ResumeShapes
import com.bryanollivie.resume.designsystem.tokens.Spacing

@Composable
fun TrainingCertificationScreen() {
    val strings = currentStrings()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = Spacing.dp6, end = Spacing.dp6, top = Spacing.dp16, bottom = 56.dp)
    ) {
        item {
            ScreenTitle(
                text = strings.trainingCertifications,
                modifier = Modifier.padding(horizontal = Spacing.dp10)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = ResumeShapes.Medium,
                colors = CardDefaults.cardColors(containerColor = ResumeColors.Surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(Spacing.dp16)) {
                    strings.certificationList.forEachIndexed { index, cert ->
                        val isInProgress = cert.name.contains("In Progress") || cert.name.contains("Em Andamento")
                        val badgeColor = if (isInProgress) ResumeColors.Accent else ResumeColors.Primary
                        val isLast = index == strings.certificationList.lastIndex

                        Row(modifier = Modifier.fillMaxWidth()) {
                            // Timeline column
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(20.dp)
                            ) {
                                // Dot
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(badgeColor)
                                )
                                // Line
                                if (!isLast) {
                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .fillMaxHeight()
                                            .defaultMinSize(minHeight = 70.dp)
                                            .background(ResumeColors.Divider)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(Spacing.dp12))

                            // Content
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = cert.name,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Spacer(modifier = Modifier.height(Spacing.dp2))
                                        Text(
                                            text = "${cert.institution} - ${cert.year}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = ResumeColors.SecondaryText
                                        )
                                    }

                                    if (isInProgress) {
                                        Spacer(modifier = Modifier.width(Spacing.dp8))
                                        TagChip(
                                            text = strings.inProgress,
                                            backgroundColor = ResumeColors.Accent.copy(alpha = 0.15f),
                                            textColor = ResumeColors.Accent,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                if (!isLast) {
                                    Spacer(modifier = Modifier.height(Spacing.dp20))
                                    HorizontalDivider(
                                        color = ResumeColors.Divider,
                                        modifier = Modifier.padding(bottom = Spacing.dp20)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
