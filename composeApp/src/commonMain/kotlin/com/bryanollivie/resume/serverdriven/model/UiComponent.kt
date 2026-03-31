package com.bryanollivie.resume.serverdriven.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Serializable
sealed interface UiComponent

@Immutable
@Serializable
@SerialName("text")
data class TextUi(
    val text: String,
    val size: Int = 16,
    val fontWeight: String = "normal",
    val color: String = "",
    val textAlign: String = "start"
) : UiComponent

@Immutable
@Serializable
@SerialName("image")
data class ImageUi(
    val url: String,
    val title: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val scaleType: String = "crop",
    val shape: String = "rectangle"
) : UiComponent

@Immutable
@Serializable
@SerialName("button")
data class ButtonUi(
    val text: String,
    val style: String = "filled",
    val action: ActionUi? = null
) : UiComponent

@Immutable
@Serializable
@SerialName("card")
data class CardUi(
    val children: List<UiComponent> = emptyList(),
    val padding: Int = 16,
    val elevation: Int = 4
) : UiComponent

@Immutable
@Serializable
@SerialName("row")
data class RowUi(
    val children: List<UiComponent> = emptyList(),
    val spacing: Int = 8,
    val alignment: String = "center"
) : UiComponent

@Immutable
@Serializable
@SerialName("column")
data class ColumnUi(
    val children: List<UiComponent> = emptyList(),
    val spacing: Int = 8
) : UiComponent

@Immutable
@Serializable
@SerialName("spacer")
data class SpacerUi(
    val height: Int = 16
) : UiComponent

@Immutable
@Serializable
@SerialName("list")
data class ListUi(
    val layout: String = "column",
    val itemSize: SizeUi = SizeUi(),
    val items: List<UiComponent> = emptyList(),
    val spacing: Int = 8
) : UiComponent

@Immutable
@Serializable
@SerialName("divider")
data class DividerUi(
    val thickness: Int = 1,
    val color: String = "#CCCCCC"
) : UiComponent

@Immutable
@Serializable
data class SizeUi(
    val width: Int = 0,
    val height: Int = 0
)

@Immutable
@Serializable
data class ActionUi(
    val actionType: String = "click",
    val destination: String = ""
)

@Immutable
@Serializable
data class ScreenUi(
    val version: Int = 1,
    val title: String = "",
    val backgroundColor: String = "",
    val components: List<UiComponent> = emptyList()
)
