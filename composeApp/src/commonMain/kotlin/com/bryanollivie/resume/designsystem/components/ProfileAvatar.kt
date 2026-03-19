package com.bryanollivie.resume.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import bryanresume.composeapp.generated.resources.Res
import bryanresume.composeapp.generated.resources.profile
import com.bryanollivie.resume.designsystem.tokens.ResumeColors
import com.bryanollivie.resume.designsystem.tokens.ResumeShapes
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.layout.size

@Composable
fun ProfileAvatar(
    size: Dp = 100.dp,
    borderWidth: Dp = 2.dp,
    borderColor: Color = ResumeColors.White,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(Res.drawable.profile),
        contentDescription = "Profile photo",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size)
            .clip(ResumeShapes.Circle)
            .border(borderWidth, borderColor, ResumeShapes.Circle)
    )
}
