<div align="center">

# 🎵 Musicify

**Free, Ad-Free Music Streaming for Android**

[![Build APK](https://github.com/felixsmkid/Musicify/actions/workflows/build.yml/badge.svg)](https://github.com/felixsmkid/Musicify/actions/workflows/build.yml)
[![Release](https://img.shields.io/github/v/release/felixsmkid/Musicify?include_prereleases&label=Latest%20Release)](https://github.com/felixsmkid/Musicify/releases)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Android](https://img.shields.io/badge/Android-8.0%2B-green.svg)](https://developer.android.com)

<br/>

*A beautiful, feature-rich music streaming app with dynamic synced lyrics — no ads, no subscriptions, completely free.*

<br/>

[📥 Download APK](https://github.com/felixsmkid/Musicify/releases/latest) • [🐛 Report Bug](https://github.com/felixsmkid/Musicify/issues) • [💡 Request Feature](https://github.com/felixsmkid/Musicify/issues)

</div>

---

## ✨ Features

### 🎶 Music Streaming
- Stream millions of songs for free
- High quality audio (up to 320kbps)
- Background playback support
- Queue management & shuffle/repeat
- Skip silence automatically

### 📝 Dynamic Lyrics
- Real-time synchronized lyrics
- Beautiful animated lyrics display
- Lyrics powered by LRCLIB (free & open source)
- Fallback to plain lyrics when synced unavailable

### 🎨 Modern Design
- Material 3 / Material You design
- Dynamic colors from album artwork
- Dark, Light, and System theme
- Smooth animations & transitions
- Edge-to-edge display

### 📚 Library Management
- Like/favorite songs
- Create custom playlists
- Recently played history
- Local offline cache

### ⚙️ Advanced Features
- Audio normalization
- Tempo & pitch control
- Sleep timer
- Equalizer support
- No ads, ever

---

## 📱 Screenshots

> Screenshots coming soon in the next release!

---

## 📥 Installation

### Download from Releases
1. Go to [Releases](https://github.com/felixsmkid/Musicify/releases)
2. Download the latest APK
3. Enable "Install from unknown sources" on your Android device
4. Install the APK

### Build from Source
```bash
git clone https://github.com/felixsmkid/Musicify.git
cd Musicify
./gradlew assembleDebug
```

The APK will be at `app/build/outputs/apk/debug/app-debug.apk`

---

## 🏗️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Design System | Material 3 |
| Audio Playback | Media3 (ExoPlayer) |
| Music Source | Piped API |
| Lyrics | LRCLIB |
| DI | Hilt |
| Networking | Retrofit + OkHttp |
| Local Storage | Room + DataStore |
| Image Loading | Coil |
| Architecture | MVVM + Repository Pattern |
| CI/CD | GitHub Actions |

---

## 🏛️ Architecture

```
com.musicify.app/
├── data/
│   ├── api/          # Retrofit API interfaces
│   ├── di/           # Hilt dependency injection modules
│   ├── model/        # Data models
│   ├── repository/   # Repository layer
│   └── local/        # Room database & DataStore
├── player/           # Media3 service & player logic
├── ui/
│   ├── screens/      # Screen composables
│   │   ├── home/     # Home screen (trending, recommendations)
│   │   ├── search/   # Search with debounce
│   │   ├── library/  # User library & playlists
│   │   ├── player/   # Full-screen player with lyrics
│   │   └── settings/ # App settings
│   ├── components/   # Reusable UI components
│   └── theme/        # Material 3 theme configuration
└── utils/            # Utility functions
```

---

## 🎯 Release Strategy

| Version Type | Tag Format | Description |
|-------------|-----------|-------------|
| Beta | `v0.x.x-beta` | Early testing release, may contain bugs |
| New Release | `v1.x.0` | Stable release with new features |
| New Update | `v1.x.x` | Feature updates to stable version |
| Bug Fixing | `v1.x.x-fix` | Patch release for specific bug fixes |

### Current Version: **BETA 0.1.0**

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the **GNU General Public License v3.0** — see the [LICENSE](LICENSE) file for details.

---

## 🙏 Credits

- [Piped](https://piped.video) — Free YouTube Music proxy API
- [LRCLIB](https://lrclib.net) — Free synced lyrics database
- [Metrolist](https://github.com/MetrolistGroup/Metrolist) — Inspiration for app architecture
- [Material 3](https://m3.material.io/) — Design system by Google

---

<div align="center">

**Made with ❤️ for music lovers**

</div>
