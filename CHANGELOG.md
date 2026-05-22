# Changelog

All notable changes to this project will be documented in this file.
See [Conventional Commits](https://www.conventionalcommits.org/) for commit guidelines.

---

## [0.2.0-beta] - 2024-01-02

### Bug Fixes

- **signing**: APK now signed with release keystore; resolves `INSTALL_PARSE_FAILED_NO_CERTIFICATES` and "package appears to be invalid" on all devices
- **build**: disabled R8 full-mode minification that was stripping required Compose runtime classes, causing a 2MB broken APK instead of the expected ~15MB
- **api**: added instance fallback list (`kavin.rocks`, `libre`, `piped.yt`, `adminforge.de`) for Piped API resilience
- **home**: fixed infinite loading spinner when trending endpoint returns non-stream items — now filters by `type == "stream"`

### New Features

- **onboarding**: added 3-page swipeable welcome flow shown on first launch (HorizontalPager with animated page indicators)
- **auth**: added Google Sign-In screen using Credential Manager API for YouTube Music library sync (requires `WEB_CLIENT_ID` configuration)
- **home/ui**: completely redesigned home screen — gradient header with contextual greeting, horizontal trending carousel with play overlay, vertical "Quick Picks" list with duration labels
- **error-state**: home screen now shows actionable retry button on API failure instead of blank screen

### Changes

- version bump to `0.2.0-beta` (versionCode 2)
- `isMinifyEnabled` set to `false` in release build until ProGuard consumer rules are properly configured per-library
- navigation flow updated: `Onboarding → Auth → Main`

### Known Issues

- Google Sign-In requires a valid `WEB_CLIENT_ID` from Google Cloud Console; currently shows UI only
- playlist sync with YouTube Music account is not yet implemented
- offline/download feature not available in this release
- lyrics display requires active playback session (no standalone lyrics search yet)

---

## [0.1.0-beta] - 2024-01-01

### Initial Release

First public beta. Core streaming architecture with Piped API backend and LRCLIB lyrics integration.

### Features

- stream audio via Piped API (YouTube Music proxy, no authentication required)
- real-time synced lyrics from LRCLIB (LRC format parsing with millisecond accuracy)
- Material 3 UI with Jetpack Compose (dynamic color, dark/light/system theme)
- Media3 ExoPlayer service with foreground notification and `MediaSession` support
- debounced search with result type filtering (`music_songs`)
- trending feed from configurable region
- library scaffold (liked songs, history, playlists — local Room DB)
- settings screen (audio quality, skip silence, normalization toggles)
- GitHub Actions CI: `build.yml` (assemble + lint + unit test), `release.yml` (tag-triggered APK publish)

### Known Issues (fixed in 0.2.0)

- ~~APK unsigned — fails to install on device~~
- ~~R8 minification produces corrupt 2MB APK~~
- ~~home screen infinite loading when API returns mixed content types~~
