@file:OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)

package com.bryanollivie.resume.serverdriven

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bryanresume.composeapp.generated.resources.Res
import com.bryanollivie.resume.serverdriven.model.ScreenUi
import com.bryanollivie.resume.serverdriven.model.UiComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import com.bryanollivie.resume.serverdriven.model.*

private val sduiJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
    classDiscriminator = "type"
    serializersModule = SerializersModule {
        polymorphic(UiComponent::class) {
            subclass(TextUi::class)
            subclass(ImageUi::class)
            subclass(ButtonUi::class)
            subclass(CardUi::class)
            subclass(RowUi::class)
            subclass(ColumnUi::class)
            subclass(SpacerUi::class)
            subclass(ListUi::class)
            subclass(DividerUi::class)
        }
    }
}

@Composable
fun ServerDrivenScreen() {
    var screenUi by remember { mutableStateOf<ScreenUi?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val jsonString = Res.readBytes("files/server_driven_ui.json").decodeToString()
            screenUi = sduiJson.decodeFromString<ScreenUi>(jsonString)
        } catch (e: Exception) {
            error = e.message ?: "Erro ao carregar UI"
        } finally {
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {
        AnimatedContent(
            targetState = isLoading,
            transitionSpec = {
                fadeIn(tween(300)) togetherWith fadeOut(tween(300))
            }
        ) { loading ->
            when {
                loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFD32F2F))
                    }
                }

                error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Erro: $error",
                            color = Color(0xFFD32F2F)
                        )
                    }
                }

                screenUi != null -> {
                    ServerDrivenContent(screenUi!!)
                }
            }
        }
    }
}

@Composable
private fun ServerDrivenContent(screen: ScreenUi) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        screen.components.forEach { component ->
            RenderUiComponent(
                component = component,
                onAction = { action ->
                    // Handle actions here - navigate, open URL, etc.
                    println("SDUI Action: ${action.actionType} -> ${action.destination}")
                }
            )
        }
    }
}
