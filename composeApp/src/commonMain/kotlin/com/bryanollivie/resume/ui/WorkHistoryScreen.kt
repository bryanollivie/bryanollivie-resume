package com.bryanollivie.resume.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WorkHistoryScreen() {
    val strings = currentStrings()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Spacing.dp16),
        verticalArrangement = Arrangement.spacedBy(Spacing.dp16)
    ) {
        item {
            ScreenTitle(text = strings.workHistory)
        }

        itemsIndexed(strings.workExperiences) { _, work ->
            SectionCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = work.company,
                            style = MaterialTheme.typography.headlineSmall,
                            color = ResumeColors.Primary
                        )
                        Spacer(modifier = Modifier.height(Spacing.dp4))
                        Text(
                            text = work.role,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        DateBadge(text = work.period)
                        Spacer(modifier = Modifier.height(Spacing.dp4))
                        Text(
                            text = work.location,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Spacing.dp16))
                HorizontalDivider(color = ResumeColors.Divider)
                Spacer(modifier = Modifier.height(Spacing.dp16))

                work.highlights.forEach { highlight ->
                    BulletItem(text = highlight)
                }

                Spacer(modifier = Modifier.height(Spacing.dp12))

                Text(
                    text = strings.technologies,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = ResumeColors.SecondaryText
                )
                Spacer(modifier = Modifier.height(Spacing.dp8))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.dp6),
                    verticalArrangement = Arrangement.spacedBy(Spacing.dp6)
                ) {
                    work.technologies.split(";").map { it.trim() }.filter { it.isNotEmpty() }.forEach { tech ->
                        TagChip(text = tech)
                    }
                }
            }
        }
    }
}
