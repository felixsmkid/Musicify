# Agents

## Cursor Cloud specific instructions

This repository is **Musicify** — a free, ad-free Android music streaming app built with Kotlin + Jetpack Compose.

### Environment Requirements
- **Java 17+** (JDK 21 available at `/usr/lib/jvm/java-21-openjdk-amd64`)
- **Android SDK** at `/opt/android-sdk` (platform 35, build-tools 35.0.0)
- `local.properties` must contain `sdk.dir=/opt/android-sdk`

### Common Commands
| Action | Command |
|--------|---------|
| Build debug APK | `./gradlew assembleDebug` |
| Build release APK | `./gradlew assembleRelease` |
| Run lint | `./gradlew lintDebug` |
| Run unit tests | `./gradlew testDebugUnitTest` |
| Clean build | `./gradlew clean` |

### Architecture Notes
- **Music source**: Piped API (YouTube Music proxy) — no API key required
- **Lyrics source**: LRCLIB — completely free, no auth needed
- **API config**: `data/api/ApiConfig.kt` has base URLs; `data/internal/conf/ServiceRegistry.kt` has Base64-encoded fallback endpoints
- **DI**: Hilt modules in `data/di/AppModule.kt`
- **Media playback**: Media3 `MusicService` in `player/` package

### Gotchas
- The Gradle wrapper is committed; use `./gradlew` directly (no need to install Gradle separately)
- `ANDROID_HOME` or `sdk.dir` in `local.properties` must be set before builds
- The project uses KSP (not KAPT) for annotation processing (Hilt, Room)
- GitHub Actions workflows use `android-actions/setup-android@v3` — no manual SDK setup needed in CI
