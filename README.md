# Bryan Ollivie - Resume App

A **Kotlin Multiplatform (KMP)** resume application built with **Compose Multiplatform**, targeting **Android**, **iOS**, and **Web (WASM)**. Features a custom design system, Lottie animations, and full internationalization (PT/EN).

## Architecture

```
composeApp/src/
├── commonMain/                          # Shared code (all platforms)
│   ├── kotlin/com/bryanollivie/resume/
│   │   ├── App.kt                       # Root composition, splash screen, navigation
│   │   ├── Platform.kt                  # Expect declarations (openUrl, shareText)
│   │   ├── theme/
│   │   │   └── Theme.kt                 # Material3 theme, typography, color scheme
│   │   ├── data/
│   │   │   ├── ResumeData.kt            # Resume content models & data
│   │   │   ├── Strings.kt              # i18n (EN/PT) with all translations
│   │   │   └── LanguageState.kt        # CompositionLocal for language state
│   │   ├── designsystem/
│   │   │   ├── tokens/
│   │   │   │   ├── Colors.kt           # Color palette (ResumeColors)
│   │   │   │   ├── Shapes.kt           # Border radius tokens (ResumeShapes)
│   │   │   │   └── Spacing.kt          # Spacing scale (Spacing.dp2..dp32)
│   │   │   └── components/
│   │   │       ├── ProfileAvatar.kt     # Circular photo with border
│   │   │       ├── AnimatedLottieButton.kt  # Clickable Lottie animation
│   │   │       ├── TransparentTabBar.kt # Bottom navigation with transparency
│   │   │       ├── LanguageSelectorBar.kt   # PT/EN toggle
│   │   │       ├── SectionCard.kt       # Card with red accent bar + title
│   │   │       ├── ScreenTitle.kt       # Page heading
│   │   │       ├── DateBadge.kt         # Year/period badge
│   │   │       ├── TagChip.kt           # Technology/status tag
│   │   │       └── BulletItem.kt        # Bullet point list item
│   │   └── ui/
│   │       ├── SummaryEducationScreen.kt
│   │       ├── WorkHistoryScreen.kt
│   │       └── TrainingCertificationScreen.kt
│   └── composeResources/
│       ├── drawable/profile.jpeg         # Profile photo
│       └── files/                        # Lottie JSON animations
│           ├── splash.json, linkedin.json, whatsapp.json, email.json, share.json
│
├── androidMain/                          # Android-specific
│   ├── kotlin/.../MainActivity.kt        # Activity entry point
│   ├── kotlin/.../Platform.android.kt    # Intent-based URL/share
│   ├── AndroidManifest.xml
│   └── res/values/strings.xml
│
├── iosMain/                              # iOS-specific
│   ├── kotlin/.../MainViewController.kt  # ComposeUIViewController bridge
│   └── kotlin/.../Platform.ios.kt        # UIApplication URL/UIActivityViewController share
│
└── wasmJsMain/                           # Web-specific
    ├── kotlin/.../Main.kt                # ComposeViewport entry point
    ├── kotlin/.../Platform.wasmJs.kt     # window.open URL/share
    └── resources/index.html

iosApp/                                   # iOS native wrapper (SwiftUI)
├── iOSApp.swift                          # @main entry
├── ContentView.swift                     # UIViewControllerRepresentable bridge
└── Info.plist
```

## Design System

### Tokens

| Token | Values |
|-------|--------|
| **Colors** | Primary (#D32F2F), PrimaryDark (#212121), Accent (#4CAF50), Surface (#FFFFFF), LinkedIn (#0A66C2), WhatsApp (#25D366) |
| **Spacing** | dp2, dp4, dp6, dp8, dp10, dp12, dp16, dp20, dp24, dp32 |
| **Shapes** | Small (6dp), Medium (8dp), Large (12dp), XLarge (16dp), Pill (20dp), Circle |

### Components

| Component | Description |
|-----------|-------------|
| `ProfileAvatar` | Circular profile image with configurable size and border |
| `AnimatedLottieButton` | Lottie animation wrapped in a clickable circle |
| `TransparentTabBar` | Bottom tab bar with transparent/translucent background |
| `LanguageSelectorBar` | PT/EN toggle with highlighted selection |
| `SectionCard` | Card container with optional red accent bar and title |
| `ScreenTitle` | Standardized page heading |
| `DateBadge` | Colored badge for dates/periods |
| `TagChip` | Small tag for technologies or status indicators |
| `BulletItem` | Text row with colored bullet point |

## Features

- **3 Tabs**: Summary & Education, Work History, Training & Certifications
- **Splash Screen**: Animated intro with profile photo, name, and Lottie animation
- **Navigation Drawer**: Side menu with profile header and settings options
- **Internationalization**: Full PT/EN support via CompositionLocal, toggle in top bar
- **Lottie Animations**: LinkedIn, WhatsApp, Email, Share icons (Compottie library)
- **Social Actions**: Open LinkedIn, WhatsApp, Email, and native Share via expect/actual
- **Design System**: Token-based colors, spacing, shapes with 9 reusable components

## Tech Stack

| Technology | Version |
|------------|---------|
| Kotlin | 2.1.0 |
| Compose Multiplatform | 1.7.3 |
| Android Gradle Plugin | 8.7.3 |
| Compottie (Lottie KMP) | 2.0.0-rc01 |
| AndroidX Lifecycle | 2.8.4 |
| Gradle | 8.10 |

**Targets**: Android (API 26+), iOS (arm64, x64, simulator), Web (WASM/JS)

## Getting Started

### Prerequisites

- JDK 17+
- Android SDK (API 35)
- Xcode (for iOS builds)
- Node.js (for Web builds)

### Run

```bash
# Android
./gradlew :composeApp:installDebug

# Web (browser)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# iOS (via Xcode)
# Open iosApp/ in Xcode and run on simulator/device
```

### Build

```bash
# Android APK
./gradlew :composeApp:assembleDebug

# Web distribution
./gradlew :composeApp:wasmJsBrowserDistribution

# All platforms
./gradlew build
```

## Platform Implementations

The app uses Kotlin's `expect/actual` mechanism for platform-specific features:

| Function | Android | iOS | Web |
|----------|---------|-----|-----|
| `openUrl()` | `Intent.ACTION_VIEW` | `UIApplication.openURL` | `window.open()` |
| `shareText()` | `Intent.ACTION_SEND` | `UIActivityViewController` | WhatsApp Web fallback |

## License

This project is for personal/portfolio use.
