package com.bryanollivie.resume.serverdriven

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bryanollivie.resume.serverdriven.model.*

@Composable
fun RenderUiComponent(
    component: UiComponent,
    modifier: Modifier = Modifier,
    onAction: (ActionUi) -> Unit = {}
) {
    when (component) {
        is TextUi -> RenderText(component, modifier)
        is ImageUi -> RenderImage(component, modifier)
        is ButtonUi -> RenderButton(component, modifier, onAction)
        is CardUi -> RenderCard(component, modifier, onAction)
        is RowUi -> RenderRow(component, modifier, onAction)
        is ColumnUi -> RenderColumn(component, modifier, onAction)
        is SpacerUi -> Spacer(modifier = Modifier.height(component.height.dp))
        is ListUi -> RenderList(component, modifier, onAction)
        is DividerUi -> RenderDivider(component, modifier)
    }
}

@Composable
private fun RenderText(textUi: TextUi, modifier: Modifier = Modifier) {
    Text(
        text = textUi.text,
        modifier = modifier.fillMaxWidth(),
        fontSize = textUi.size.sp,
        fontWeight = textUi.fontWeight.toFontWeight(),
        color = textUi.color.toColor(Color.White),
        textAlign = textUi.textAlign.toTextAlign()
    )
}

@Composable
private fun RenderImage(imageUi: ImageUi, modifier: Modifier = Modifier) {
    val shape = when (imageUi.shape) {
        "circle" -> CircleShape
        "rounded" -> RoundedCornerShape(12.dp)
        else -> RoundedCornerShape(0.dp)
    }
    // Placeholder colored box since we can't load network images without a library
    Box(
        modifier = modifier
            .then(
                if (imageUi.width > 0 && imageUi.height > 0) {
                    Modifier.size(imageUi.width.dp, imageUi.height.dp)
                } else {
                    Modifier.fillMaxWidth().height(200.dp)
                }
            )
            .clip(shape)
            .background(Color(0xFF333333)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = imageUi.title.ifEmpty { "Image" },
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun RenderButton(
    buttonUi: ButtonUi,
    modifier: Modifier = Modifier,
    onAction: (ActionUi) -> Unit
) {
    when (buttonUi.style) {
        "filled" -> Button(
            onClick = { buttonUi.action?.let(onAction) },
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD32F2F)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = buttonUi.text, color = Color.White)
        }

        "outlined" -> OutlinedButton(
            onClick = { buttonUi.action?.let(onAction) },
            modifier = modifier,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFD32F2F)
            )
        ) {
            Text(text = buttonUi.text)
        }

        else -> TextButton(
            onClick = { buttonUi.action?.let(onAction) },
            modifier = modifier
        ) {
            Text(text = buttonUi.text, color = Color(0xFFD32F2F))
        }
    }
}

@Composable
private fun RenderCard(
    cardUi: CardUi,
    modifier: Modifier = Modifier,
    onAction: (ActionUi) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = cardUi.elevation.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        )
    ) {
        Column(
            modifier = Modifier.padding(cardUi.padding.dp)
        ) {
            cardUi.children.forEach { child ->
                RenderUiComponent(child, onAction = onAction)
            }
        }
    }
}

@Composable
private fun RenderRow(
    rowUi: RowUi,
    modifier: Modifier = Modifier,
    onAction: (ActionUi) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(rowUi.spacing.dp),
        verticalAlignment = when (rowUi.alignment) {
            "top" -> Alignment.Top
            "bottom" -> Alignment.Bottom
            else -> Alignment.CenterVertically
        }
    ) {
        rowUi.children.forEach { child ->
            Box(modifier = Modifier.weight(1f, fill = false)) {
                RenderUiComponent(child, onAction = onAction)
            }
        }
    }
}

@Composable
private fun RenderColumn(
    columnUi: ColumnUi,
    modifier: Modifier = Modifier,
    onAction: (ActionUi) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(columnUi.spacing.dp)
    ) {
        columnUi.children.forEach { child ->
            RenderUiComponent(child, onAction = onAction)
        }
    }
}

@Composable
private fun RenderList(
    listUi: ListUi,
    modifier: Modifier = Modifier,
    onAction: (ActionUi) -> Unit
) {
    when (listUi.layout) {
        "row" -> Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(listUi.spacing.dp)
        ) {
            listUi.items.forEach { item ->
                RenderUiComponent(item, onAction = onAction)
            }
        }

        else -> Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(listUi.spacing.dp)
        ) {
            listUi.items.forEach { item ->
                RenderUiComponent(item, onAction = onAction)
            }
        }
    }
}

@Composable
private fun RenderDivider(dividerUi: DividerUi, modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier.fillMaxWidth(),
        thickness = dividerUi.thickness.dp,
        color = dividerUi.color.toColor(Color(0xFFCCCCCC))
    )
}

// --- Extension helpers ---

private fun String.toFontWeight(): FontWeight = when (this) {
    "bold" -> FontWeight.Bold
    "semibold" -> FontWeight.SemiBold
    "medium" -> FontWeight.Medium
    "light" -> FontWeight.Light
    "thin" -> FontWeight.Thin
    else -> FontWeight.Normal
}

private fun String.toTextAlign(): TextAlign = when (this) {
    "center" -> TextAlign.Center
    "end" -> TextAlign.End
    "justify" -> TextAlign.Justify
    else -> TextAlign.Start
}

private fun String.toColor(default: Color): Color {
    if (this.isBlank()) return default
    return try {
        Color(this.removePrefix("#").toLong(16).let {
            if (this.length - (if (this.startsWith("#")) 1 else 0) == 6) {
                it or 0xFF000000
            } else {
                it
            }
        }.toInt())
    } catch (_: Exception) {
        default
    }
}
