# Changelog

All notable changes to this project will be documented in this file.
See [Conventional Commits](https://www.conventionalcommits.org/) for commit guidelines.

---

## [0.3.0-beta] - 2024-01-03

### New Features

- **innertube-backend**: replaced unreliable Piped API proxy with direct InnerTube calls to `music.youtube.com/youtubei/v1/` — this resolves persistent loading failures caused by community Piped instances being offline (HTTP 526/502)
- **google-auth**: fully integrated Google Sign-In using Credential Manager + `GetGoogleIdOption`; on successful auth the app receives `displayName`, `email`, and `profilePictureUri` from the Google ID token
- **home-feed**: home screen now fetches trending tracks via InnerTube search endpoint with curated query, ensuring results always contain playable items with thumbnails and metadata
- **credential-obfuscation**: WEB_CLIENT_ID stored Base64-encoded in `ServiceRegistry` to avoid plain-text exposure in public repository

### Bug Fixes

- **home-blank**: fixed home screen showing empty state — root cause was InnerTube charts endpoint returning playlist references (browseEndpoint) instead of directly playable videoIds; switched to search-based feed
- **onboarding-popup**: removed onboarding/auth flow from main navigation that was triggering on every app launch (state was never persisted to DataStore); app now launches directly to main screen
- **parser**: fixed `InnerTubeParser` not extracting `videoId` — added `playlistItemData` field extraction and artist text concatenation from multiple `runs`

### Changes

- version bump to `0.3.0-beta` (versionCode 3)
- added `androidx.credentials:credentials:1.3.0` and `googleid:1.1.1` dependencies
- `MusicRepository.searchTrending()` uses InnerTube search with fallback query if primary returns empty
- `InnerTubeClient` uses `ANDROID_MUSIC` client context for player requests (required for audio stream URLs)

---

## [0.2.0-beta] - 2024-01-02

### Bug Fixes

- **signing**: APK signed with release keystore; resolves `INSTALL_PARSE_FAILED_NO_CERTIFICATES`
- **build**: disabled R8 minification that produced corrupt 2MB APK
- **api**: added multi-instance fallback list for Piped API

### Features

- onboarding: 3-page swipeable welcome flow (HorizontalPager)
- auth: Google Sign-In UI scaffold
- home: redesigned with gradient header, trending carousel, quick picks list
- error-state: retry button on API failure

### Changes

- signing config added to release build
- version bump to 0.2.0-beta (versionCode 2)
- minification disabled pending ProGuard rule audit

### Known Issues (fixed in 0.3.0)

- ~~home screen blank due to Piped API downtime~~
- ~~onboarding popup appearing on every launch~~
- ~~Google Sign-In non-functional without WEB_CLIENT_ID~~

---

## [0.1.0-beta] - 2024-01-01

### Initial Release

Core streaming architecture with Piped API backend and LRCLIB lyrics integration.

- stream audio via Piped API (YouTube Music proxy)
- real-time synced lyrics from LRCLIB
- Material 3 UI with Jetpack Compose
- Media3 ExoPlayer with foreground service
- search, trending, library, settings screens
- GitHub Actions CI/CD

### Known Issues (fixed in 0.2.0)

- ~~APK unsigned — install failure~~
- ~~R8 produces 2MB corrupt APK~~
