# Changelog

All notable changes to this project will be documented in this file.

---

## [1.0.0] - 2024-01-10

### Release — v1.0.0 Stable

First stable release of Musicify.

### Playback Engine
- Rewrote audio streaming pipeline: InnerTube ANDROID_MUSIC client with proper device headers (User-Agent, X-YouTube-Client-Name, X-YouTube-Client-Version) for authenticated-like requests from real Android devices
- Multi-source fallback: InnerTube → 5 Piped instances (sequential retry)
- ExoPlayer with MediaSession for background playback and notification controls
- Error handling with user-visible Toast on stream failure

### UI/UX
- Home screen: Spotify-style layout with profile icon, Featured horizontal cards with gradient overlay, Trending Songs vertical list with duration
- Search: debounced input with Song/Video type detection, clickable results that trigger playback
- Library: gradient cards (Liked Songs, Recently Played, Downloads), playlist section
- Settings: grouped sections (Audio, Appearance, Lyrics, About) with Google account card at top
- Mini Player: persistent bottom bar showing now-playing with album art, play/pause toggle
- Onboarding: 3-page welcome flow on first install only (SharedPreferences persistence)

### Google Sign-In
- Credential Manager integration with WEB_CLIENT_ID
- Profile shown in Settings after successful auth
- Error displayed inline if auth fails

### Technical
- InnerTube search with songs filter for reliable results with videoId + duration
- Parser extracts duration from flexColumn runs, play count from flexCol 2
- Hilt DI with proper OkHttp interceptor chain for YouTube headers
- Version: 1.0.0, versionCode 10

### Known Limitations
- Playback depends on YouTube's ANDROID_MUSIC client accepting requests from device
- If all stream sources fail, a Toast is shown and user should try another song
- Google Sign-In requires properly configured OAuth consent screen (published, not testing)
- Lyrics feature (LRCLIB) ready in code but full UI not yet connected

---

## [0.3.0-beta] - 2024-01-03

### Features
- InnerTube backend replacing Piped API
- Google Sign-In via Credential Manager
- Home feed via InnerTube search
- Multi-source stream fallback

### Bug Fixes
- Home screen blank (charts endpoint returning playlists)
- Onboarding popup on every launch
- InnerTube parser videoId extraction

---

## [0.2.0-beta] - 2024-01-02

### Bug Fixes
- APK signing (INSTALL_PARSE_FAILED_NO_CERTIFICATES)
- R8 minification producing 2MB corrupt APK

---

## [0.1.0-beta] - 2024-01-01

### Initial Release
- Core streaming architecture
- Material 3 UI, Media3 ExoPlayer, Hilt DI
- GitHub Actions CI/CD
