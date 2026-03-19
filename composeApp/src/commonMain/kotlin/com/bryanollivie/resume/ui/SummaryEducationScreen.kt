package com.bryanollivie.resume.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bryanollivie.resume.data.*
import com.bryanollivie.resume.designsystem.components.*
import com.bryanollivie.resume.designsystem.tokens.ResumeColors
import com.bryanollivie.resume.designsystem.tokens.Spacing
import com.bryanollivie.resume.openUrl
import com.bryanollivie.resume.shareText

@Composable
fun SummaryEducationScreen() {
    val info = ResumeData.personalInfo
    val strings = currentStrings()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 56.dp),
        verticalArrangement = Arrangement.spacedBy(Spacing.dp8)
    ) {
        // Header full-width
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ResumeColors.Black)
                    .padding(horizontal = Spacing.dp16, vertical = Spacing.dp16),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileAvatar(size = 90.dp)
                Spacer(modifier = Modifier.height(Spacing.dp8))
                Text(
                    text = info.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = ResumeColors.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(Spacing.dp4))
                Text(
                    text = strings.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = ResumeColors.Primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(Spacing.dp6))
                Text(
                    text = info.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = ResumeColors.White.copy(alpha = 0.7f)
                )
                Text(
                    text = "${info.email} | ${info.phone}",
                    style = MaterialTheme.typography.bodySmall,
                    color = ResumeColors.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(Spacing.dp10))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.dp12),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedLottieButton(
                        animationFile = "linkedin.json",
                        onClick = { openUrl(info.linkedin) }
                    )
                    AnimatedLottieButton(
                        animationFile = "whatsapp.json",
                        onClick = { openUrl("https://wa.me/5511971721750") }
                    )
                    AnimatedLottieButton(
                        animationFile = "email.json",
                        size = 49.dp,
                        onClick = { openUrl("mailto:${info.email}") }
                    )
                    AnimatedLottieButton(
                        animationFile = "share.json",
                        onClick = {
                            shareText("${info.name} - ${info.title}\n${info.email}\n${info.phone}\n${info.linkedin}")
                        }
                    )
                }
            }
        }

        // Summary
        item {
            SectionCard(
                title = strings.summary,
                modifier = Modifier.padding(horizontal = Spacing.dp6)
            ) {
                Text(
                    text = strings.summaryText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Languages
        item {
            SectionCard(
                title = strings.languages,
                modifier = Modifier.padding(horizontal = Spacing.dp6)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.dp12)) {
                    strings.languageList.forEach { lang ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = lang.name,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = lang.level,
                                style = MaterialTheme.typography.bodyMedium,
                                color = ResumeColors.SecondaryText
                            )
                        }
                    }
                }
            }
        }

        // Education
        item {
            SectionCard(
                title = strings.education,
                modifier = Modifier
                    .padding(horizontal = Spacing.dp16)
                    .padding(bottom = Spacing.dp8)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.dp16)) {
                    strings.educationList.forEach { edu ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            DateBadge(text = edu.year)
                            Spacer(modifier = Modifier.width(Spacing.dp12))
                            Column {
                                Text(
                                    text = edu.degree,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = edu.institution,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
