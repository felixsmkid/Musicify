# 📋 Changelog

All notable changes to **Musicify** will be documented in this file.

Format based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) and [Semantic Versioning](https://semver.org/).

---

## [v0.1.0-beta] — 2024-01-01

### 🎉 BETA 0.1 — Initial Beta Release

First public beta of Musicify! A completely free, ad-free music streaming app for Android.

### ✅ Added

**Core Features**
- 🎶 Music streaming via Piped API (YouTube Music proxy)
- 📝 Real-time synchronized lyrics (LRCLIB integration)
- 🔍 Search songs, artists, and albums with debounce
- 📈 Trending music discovery
- 📚 Library management (Liked Songs, History, Playlists)
- ▶️ Background playback with media notification
- 🔀 Shuffle, repeat, and queue management

**UI & Design**
- 🎨 Material 3 / Material You design system
- 🌙 Dark, Light, and System theme support
- 🎭 Dynamic colors from album artwork
- ✨ Smooth animations and transitions
- 📱 Edge-to-edge display

**Player**
- 🎵 Full-screen Now Playing with album art
- 📝 Animated synced lyrics overlay
- ⏩ Skip silence option
- 🔊 Audio normalization
- 🎚️ Audio quality selection (up to 320kbps)

**Technical**
- 🏗️ Kotlin + Jetpack Compose architecture
- 🎧 Media3 (ExoPlayer) audio engine
- 💉 Hilt dependency injection (KSP)
- 💾 Room database + DataStore preferences
- 🌐 Retrofit + OkHttp networking
- 🖼️ Coil image loading
- ⚙️ GitHub Actions CI/CD (build, lint, test, release)

### ⚠️ Known Issues
- Playlist sync not yet available
- Some tracks may not have synced lyrics
- Download/offline feature coming in next release
- Release APK is unsigned (install from unknown sources required)

---

## Release Types

| Emoji | Type | Tag Format | Description |
|-------|------|-----------|-------------|
| 🧪 | Beta | `v0.x.x-beta` | Testing phase, may have bugs |
| 🎉 | New Release | `v1.0.0` | First stable, fully tested |
| ⬆️ | New Update | `v1.x.0` | New features added |
| 🔧 | Bug Fixing | `v1.x.x` | Specific fixes (listed in detail) |

---

## Upcoming (Next Release)

- [ ] Offline download support
- [ ] Playlist import/export
- [ ] Equalizer UI
- [ ] Artist & album detail pages
- [ ] Sleep timer with fade out
- [ ] Signed APK release
