# Changelog

All notable changes to Musicify will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.1.0-beta] - 2024-01-01

### 🎉 Initial Beta Release

This is the first beta release of Musicify! A free, ad-free music streaming app for Android.

### Added
- **Music Streaming** - Stream music for free via Piped API (YouTube Music proxy)
- **Dynamic Synced Lyrics** - Real-time synchronized lyrics powered by LRCLIB
- **Search** - Search for any song, artist, or album
- **Trending** - Discover trending music
- **Library** - Save your favorite songs and create playlists
- **Modern UI** - Material 3 design with dynamic colors (Material You)
- **Background Playback** - Keep listening while using other apps
- **No Ads** - Completely ad-free experience
- **Dark/Light Theme** - System-aware theming with dynamic color support
- **Settings** - Audio quality, skip silence, normalization, and more

### Technical
- Built with Kotlin + Jetpack Compose
- Media3 (ExoPlayer) for audio playback
- Hilt for dependency injection
- Room database for local storage
- Retrofit + OkHttp for networking
- GitHub Actions CI/CD for automated builds

### Known Issues
- Playlist sync is not yet available
- Some rare tracks may not have synced lyrics available
- Download feature coming in next release

---

## Release Types

| Tag | Description |
|-----|-------------|
| `BETA x.x` | Beta release - may contain bugs |
| `New Release` | Stable release |
| `New Update` | Feature update to stable |
| `Bug Fixing` | Patch release fixing specific bugs |
