package com.bryanollivie.resume.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bryanollivie.resume.designsystem.tokens.ResumeColors

data class TabItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun TransparentTabBar(
    tabs: List<TabItem>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = ResumeColors.White.copy(alpha = 0.8f),
    selectedColor: Color = ResumeColors.Primary,
    unselectedColor: Color = Color.Gray
) {
    val bottomInset = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = selectedIndex == index
                val color = if (isSelected) selectedColor else unselectedColor

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTabSelected(index) }
                        .padding(vertical = 4.dp)
                ) {
                    Icon(
                        tab.icon,
                        contentDescription = tab.title,
                        modifier = Modifier.size(20.dp),
                        tint = color
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        tab.title,
                        fontSize = 11.sp,
                        color = color
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(bottomInset))
    }
}
