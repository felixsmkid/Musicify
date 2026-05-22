<div align="center">

# Musicify

**Free music streaming client for Android**

[![CI](https://github.com/felixsmkid/Musicify/actions/workflows/build.yml/badge.svg)](https://github.com/felixsmkid/Musicify/actions/workflows/build.yml)
[![Release](https://img.shields.io/github/v/release/felixsmkid/Musicify?include_prereleases&style=flat-square)](https://github.com/felixsmkid/Musicify/releases/latest)
[![License](https://img.shields.io/badge/license-GPL--3.0-blue?style=flat-square)](LICENSE)
[![Min SDK](https://img.shields.io/badge/minSdk-26-green?style=flat-square)](https://developer.android.com)

[Download APK](https://github.com/felixsmkid/Musicify/releases/latest) · [Issues](https://github.com/felixsmkid/Musicify/issues) · [Changelog](CHANGELOG.md)

</div>

---

## Overview

Musicify is an open-source Android application that provides ad-free music streaming with real-time synchronized lyrics. Built with Kotlin and Jetpack Compose, it leverages the Piped API as a YouTube Music proxy and LRCLIB for lyric synchronization.

No account required. No ads. No telemetry.

---

## Features

**Streaming & Playback**
- High-quality audio streaming (up to 320kbps OPUS/AAC)
- Background playback with MediaSession integration
- Foreground service with persistent notification controls
- Queue management, shuffle, repeat modes
- Skip silence detection and audio normalization

**Lyrics**
- Real-time synced lyrics (LRC format, millisecond precision)
- Multiple provider fallback via LRCLIB search API
- Animated highlight with smooth scrolling

**Discovery**
- Regional trending feed
- Debounced full-text search with type filtering
- Artist and album navigation

**Library**
- Local favorites and playlist management (Room)
- Listening history with timestamp
- Google account sync for YouTube Music liked songs (Credential Manager)

**User Experience**
- Material 3 with dynamic color extraction from album art
- Dark / Light / System theme
- Swipeable onboarding flow on first launch
- Edge-to-edge rendering with gesture nav support

---

## Architecture

```
com.musicify.app
├── data
│   ├── api            Retrofit interfaces (PipedApi, LrcLibApi)
│   ├── di             Hilt modules (Singleton scope)
│   ├── model          Kotlin data classes (immutable)
│   ├── repository     Single-source-of-truth abstraction
│   ├── local          Room entities + DAOs
│   └── internal/conf  Obfuscated endpoint registry
├── player             Media3 MediaSessionService
├── ui
│   ├── screens        Feature-based screen packages
│   │   ├── onboarding HorizontalPager welcome flow
│   │   ├── auth       Google Sign-In (Credential Manager)
│   │   ├── home       Trending + Quick Picks
│   │   ├── search     Debounced query + results
│   │   ├── library    Favorites / History / Playlists
│   │   ├── player     Now Playing + Lyrics overlay
│   │   └── settings   Preferences (DataStore)
│   ├── components     Shared composables
│   └── theme          MaterialTheme configuration
└── utils              Extension functions
```

Pattern: **MVVM + Repository** with unidirectional data flow via `StateFlow`.

---

## Tech Stack

| Layer | Implementation |
|-------|---------------|
| Language | Kotlin 2.1, coroutines + Flow |
| UI toolkit | Jetpack Compose (BOM 2024.12) |
| Design system | Material 3, dynamic color |
| Media | Media3 1.5 (ExoPlayer) |
| Networking | Retrofit 2.11 + OkHttp 4.12 |
| Serialization | Gson |
| DI | Hilt 2.53 (KSP) |
| Persistence | Room 2.6 + DataStore |
| Image loading | Coil 2.7 (Compose) |
| Auth | Credential Manager + Google Identity |
| CI/CD | GitHub Actions |

---

## Build

Requirements: JDK 17+, Android SDK 35, Gradle 8.11

```bash
git clone https://github.com/felixsmkid/Musicify.git
cd Musicify
./gradlew assembleDebug        # debug APK (unsigned)
./gradlew assembleRelease      # release APK (signed)
```

Output: `app/build/outputs/apk/{debug,release}/`

### Signing

The release keystore (`app/release-key.jks`) is included for reproducible builds during beta. Production releases will use a separate CI-managed key.

---

## Configuration

**Piped Instances** — configured in `ApiConfig.kt` with automatic fallback:
```
pipedapi.kavin.rocks (primary)
pipedapi-libre.kavin.rocks
api.piped.yt
pipedapi.adminforge.de
```

**Google Sign-In** — requires a `WEB_CLIENT_ID` from [Google Cloud Console](https://console.cloud.google.com/apis/credentials). Set in `AuthScreen.kt`.

---

## Release Strategy

| Version pattern | Type | Description |
|----------------|------|-------------|
| `v0.x.x-beta` | Beta | Pre-release, may contain breaking issues |
| `v1.0.0` | Stable | First production-ready release |
| `v1.x.0` | Feature | New functionality added |
| `v1.x.y` | Patch | Bug fixes only |

Releases are triggered by pushing a tag matching `v*` — the `release.yml` workflow builds and publishes the APK to GitHub Releases automatically.

---

## Contributing

1. Fork → branch (`feature/xyz`) → commit → push → PR
2. Follow existing code style (ktlint-compatible)
3. Include relevant unit tests for new business logic
4. UI changes should include screenshots in the PR description

---

## License

This project is licensed under the [GNU General Public License v3.0](LICENSE).

```
Copyright (C) 2024 felixsmkid
SPDX-License-Identifier: GPL-3.0-only
```
