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
import androidx.compose.ui.unit.sp
import bryanresume.composeapp.generated.resources.Res
import com.bryanollivie.resume.serverdriven.model.ScreenUi
import com.bryanollivie.resume.serverdriven.model.UiComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import com.bryanollivie.resume.openUrl
import com.bryanollivie.resume.shareText
import com.bryanollivie.resume.serverdriven.model.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

val sduiJson = Json {
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
            subclass(AvatarUi::class)
            subclass(LottieButtonUi::class)
            subclass(BadgeUi::class)
            subclass(SectionCardUi::class)
            subclass(BoxUi::class)
        }
    }
}

/**
 * Server-Driven Screen that loads UI from a remote URL or falls back to a local JSON file.
 *
 * @param remoteUrl URL to fetch JSON from (e.g. GitHub Gist raw URL)
 * @param localFallback Local JSON file name in composeResources/files/ as fallback
 */
@Composable
fun ServerDrivenScreen(
    remoteUrl: String? = null,
    localFallback: String = "server_driven_ui.json"
) {
    var screenUi by remember { mutableStateOf<ScreenUi?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var source by remember { mutableStateOf("") }

    LaunchedEffect(remoteUrl, localFallback) {
        isLoading = true
        try {
            val jsonString = if (remoteUrl != null) {
                try {
                    val client = HttpClient()
                    val response: String = client.get(remoteUrl).bodyAsText()
                    client.close()
                    source = "remote"
                    response
                } catch (e: Exception) {
                    // Fallback to local if remote fails
                    source = "local (remote failed)"
                    Res.readBytes("files/$localFallback").decodeToString()
                }
            } else {
                source = "local"
                Res.readBytes("files/$localFallback").decodeToString()
            }
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
                    ServerDrivenContent(screenUi!!, source)
                }
            }
        }
    }
}

@Composable
private fun ServerDrivenContent(screen: ScreenUi, source: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        screen.components.forEach { component ->
            RenderUiComponent(
                component = component,
                onAction = { action ->
                    when (action.actionType) {
                        "open_url" -> openUrl(action.destination)
                        "share" -> shareText(action.destination)
                        else -> println("SDUI Action: ${action.actionType} -> ${action.destination}")
                    }
                }
            )
        }

        // Source indicator
        Text(
            text = "Source: $source | v${screen.version}",
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
            fontSize = 10.sp,
            color = Color(0xFF555555),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
