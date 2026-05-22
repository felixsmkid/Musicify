# Agents

## Cursor Cloud specific instructions

This is **Musicify**, an Android Kotlin app (music streaming via Piped API + LRCLIB lyrics). Package: `com.musicify.app`.

### Environment

- **Java**: OpenJDK 21 at `/usr/lib/jvm/java-21-openjdk-amd64`
- **Android SDK**: `/opt/android-sdk` (platform 35, build-tools 35.0.0)
- **Gradle**: 8.11.1 (via wrapper `./gradlew`)
- Environment variables `JAVA_HOME` and `ANDROID_HOME` must be set (added to `~/.bashrc`).
- `local.properties` has `sdk.dir=/opt/android-sdk`.

### Build / Lint / Test

```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export ANDROID_HOME=/opt/android-sdk

./gradlew assembleDebug        # Build debug APK
./gradlew lintDebug            # Run lint
./gradlew testDebugUnitTest    # Run unit tests
```

### Notes

- This is an Android-only project; there is no web frontend or backend server to start.
- The "hello world" for this app is a successful `assembleDebug` producing an APK (no emulator available in cloud).
- Kotlin compile warnings exist for deprecated Material Icons (`PlaylistPlay`, `VolumeUp`) — use `AutoMirrored` variants if updating those screens.
- No secrets or API keys are required; Piped API and LRCLIB are public/unauthenticated.
