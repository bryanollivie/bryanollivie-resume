package com.bryanollivie.resume

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bryanollivie.resume.data.*
import com.bryanollivie.resume.designsystem.components.LanguageSelectorBar
import com.bryanollivie.resume.designsystem.components.ProfileAvatar
import com.bryanollivie.resume.designsystem.components.TransparentTabBar
import com.bryanollivie.resume.designsystem.components.TabItem
import com.bryanollivie.resume.designsystem.tokens.ResumeColors
import com.bryanollivie.resume.theme.*
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.LottieConstants
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.ui.layout.ContentScale
import bryanresume.composeapp.generated.resources.Res
import bryanresume.composeapp.generated.resources.profile
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import com.bryanollivie.resume.ui.SummaryEducationScreen
import com.bryanollivie.resume.ui.TrainingCertificationScreen
import com.bryanollivie.resume.ui.UtilsDemoScreen
import com.bryanollivie.resume.ui.WorkHistoryScreen
import com.bryanollivie.resume.serverdriven.ServerDrivenScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val languageState = remember { mutableStateOf(AppLanguage.EN) }

    CompositionLocalProvider(LocalLanguage provides languageState) {
    ResumeTheme {
        var showSplash by remember { mutableStateOf(true) }

        AnimatedContent(
            targetState = showSplash,
            transitionSpec = {
                fadeIn(animationSpec = tween(500)) togetherWith
                        fadeOut(animationSpec = tween(500))
            }
        ) { isSplash ->
            if (isSplash) {
                SplashScreen(onFinished = { showSplash = false })
            } else {
                MainContent()
            }
        }
    }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainContent() {
        val strings = currentStrings()
        val languageState = LocalLanguage.current
        var selectedTab by remember { mutableStateOf(0) }
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val tabs = listOf(
            TabItem(strings.tabSummary, Icons.Default.Person),
            TabItem(strings.tabWorkHistory, Icons.Default.DateRange),
            TabItem(strings.tabCertifications, Icons.Default.Star),
            TabItem(strings.tabUtils, Icons.Default.Build),
            TabItem(strings.tabServerDriven, Icons.Default.Refresh)
        )

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    onCloseDrawer = { scope.launch { drawerState.close() } }
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = tabs[selectedTab].title,
                                style = MaterialTheme.typography.titleLarge,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            LanguageSelectorBar(
                                currentLanguage = languageState.value,
                                onLanguageSelected = { languageState.value = it },
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = androidx.compose.ui.graphics.Color.Black,
                            titleContentColor = androidx.compose.ui.graphics.Color.White,
                            navigationIconContentColor = androidx.compose.ui.graphics.Color.White
                        )
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding())) {
                    when (selectedTab) {
                        0 -> ServerDrivenScreen(
                            remoteUrl = "https://gist.githubusercontent.com/bryanollivie/b16451c24126f597137d58a504d836da/raw/home_sdui.json",
                            localFallback = "home_sdui.json"
                        )
                        1 -> WorkHistoryScreen()
                        2 -> TrainingCertificationScreen()
                        3 -> UtilsDemoScreen()
                        4 -> ServerDrivenScreen(localFallback = "server_driven_ui.json")
                    }

                    TransparentTabBar(
                        tabs = tabs,
                        selectedIndex = selectedTab,
                        onTabSelected = { selectedTab = it },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SplashScreen(onFinished: () -> Unit) {
    val avatarScale = remember { Animatable(0f) }
    val nameAlpha = remember { Animatable(0f) }
    val titleAlpha = remember { Animatable(0f) }

    var splashJson by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        splashJson = try {
            Res.readBytes("files/splash.json").decodeToString()
        } catch (_: Exception) { null }
    }

    val splashComposition by rememberLottieComposition {
        splashJson?.let { LottieCompositionSpec.JsonString(it) }
            ?: LottieCompositionSpec.JsonString("{}")
    }
    val splashProgress by animateLottieCompositionAsState(
        splashComposition,
        iterations = 1
    )

    LaunchedEffect(Unit) {
        avatarScale.animateTo(1f, animationSpec = tween(600, easing = FastOutSlowInEasing))
        nameAlpha.animateTo(1f, animationSpec = tween(400))
        titleAlpha.animateTo(1f, animationSpec = tween(400))
        delay(1500)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ProfileAvatar(
                size = 120.dp,
                modifier = Modifier.scale(avatarScale.value)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Bryan Ollivie",
                style = MaterialTheme.typography.headlineLarge,
                color = androidx.compose.ui.graphics.Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer { alpha = nameAlpha.value }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "SOFTWARE ENGINEER",
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryRed,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp,
                modifier = Modifier.graphicsLayer { alpha = titleAlpha.value }
            )
            Spacer(modifier = Modifier.height(24.dp))
            LottieAnimation(
                composition = splashComposition,
                progress = { splashProgress },
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Composable
private fun DrawerContent(onCloseDrawer: () -> Unit) {
    val info = ResumeData.personalInfo
    val strings = currentStrings()

    ModalDrawerSheet(
        modifier = Modifier.width(300.dp)
    ) {
        // Header with photo and email
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(androidx.compose.ui.graphics.Color.Black)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileAvatar(size = 96.dp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = info.name,
                style = MaterialTheme.typography.titleLarge,
                color = androidx.compose.ui.graphics.Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = info.email,
                style = MaterialTheme.typography.bodyMedium,
                color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Menu items
        DrawerMenuItem(
            icon = Icons.Default.Settings,
            label = strings.settings,
            onClick = onCloseDrawer
        )
        DrawerMenuItem(
            icon = Icons.Default.Notifications,
            label = strings.notifications,
            onClick = onCloseDrawer
        )
        DrawerMenuItem(
            icon = Icons.Default.Lock,
            label = strings.privacy,
            onClick = onCloseDrawer
        )

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

        DrawerMenuItem(
            icon = Icons.Default.Share,
            label = strings.shareResume,
            onClick = onCloseDrawer
        )
        DrawerMenuItem(
            icon = Icons.Default.Info,
            label = strings.about,
            onClick = onCloseDrawer
        )
    }
}

@Composable
private fun DrawerMenuItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) },
        selected = false,
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 12.dp)
    )
}
