<div align="center">

<img src="https://raw.githubusercontent.com/felixsmkid/Musicify/main/.github/assets/banner.png" alt="Musicify Banner" width="100%" />

# 🎵 Musicify

### *Your Music, Your Way — Free Forever*

[![Build](https://github.com/felixsmkid/Musicify/actions/workflows/build.yml/badge.svg)](https://github.com/felixsmkid/Musicify/actions/workflows/build.yml)
[![Release](https://img.shields.io/github/v/release/felixsmkid/Musicify?include_prereleases&style=flat-square&label=Download&color=6C63FF)](https://github.com/felixsmkid/Musicify/releases/latest)
[![License](https://img.shields.io/badge/License-GPL%20v3-blue.svg?style=flat-square)](LICENSE)
[![Android](https://img.shields.io/badge/Android-8.0%2B-brightgreen.svg?style=flat-square)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1-purple.svg?style=flat-square)](https://kotlinlang.org)

<br/>

**A beautiful, feature-rich music streaming app with real-time synced lyrics.**  
No ads. No subscriptions. No tracking. Completely free and open source.

<br/>

[<img src="https://img.shields.io/badge/📥_Download_APK-6C63FF?style=for-the-badge&logoColor=white" alt="Download" />](https://github.com/felixsmkid/Musicify/releases/latest)

<br/>

[Report Bug](https://github.com/felixsmkid/Musicify/issues) · [Request Feature](https://github.com/felixsmkid/Musicify/issues) · [Changelog](CHANGELOG.md)

</div>

---

## 🌟 Why Musicify?

| | Musicify | Others |
|---|---|---|
| 💰 Price | **Free forever** | Subscriptions |
| 🚫 Ads | **None** | Forced ads |
| 📝 Lyrics | **Real-time synced** | Premium only |
| 🎨 Design | **Material You** | Outdated |
| 🔒 Privacy | **No tracking** | Data collection |
| 📱 Size | **~22MB** | 100MB+ |

---

## ✨ Features

<table>
<tr>
<td width="50%">

### 🎶 Music & Playback
- Stream millions of songs for free
- High quality audio (up to 320kbps)
- Background playback
- Queue management
- Shuffle & repeat modes
- Skip silence automatically
- Audio normalization
- Sleep timer

</td>
<td width="50%">

### 📝 Dynamic Lyrics
- Real-time synchronized lyrics
- Beautiful animated display
- Auto-scrolling with highlight
- Multiple lyrics sources
- Fallback to plain text
- Adjustable font size

</td>
</tr>
<tr>
<td width="50%">

### 🎨 Modern Design
- Material 3 / Material You
- Dynamic colors from album art
- Dark, Light & System theme
- Smooth animations
- Edge-to-edge display
- Gesture navigation support

</td>
<td width="50%">

### 📚 Library & More
- Like / favorite songs
- Custom playlists
- Listening history
- Search with autocomplete
- Trending & discover
- Artist & album pages

</td>
</tr>
</table>

---

## 📱 Screenshots

<div align="center">

> 📸 Screenshots will be added in the next stable release

</div>

---

## 📥 Installation

### Download Pre-built APK
1. Go to [**Releases**](https://github.com/felixsmkid/Musicify/releases/latest)
2. Download `app-release-unsigned.apk`
3. Install on your Android device (enable "Unknown sources" if prompted)

### Build from Source
```bash
git clone https://github.com/felixsmkid/Musicify.git
cd Musicify
./gradlew assembleDebug
```
APK output: `app/build/outputs/apk/debug/app-debug.apk`

---

## 🏗️ Tech Stack

| Layer | Technology |
|-------|-----------|
| **Language** | Kotlin 2.1 |
| **UI** | Jetpack Compose + Material 3 |
| **Architecture** | MVVM + Repository Pattern |
| **Audio** | Media3 / ExoPlayer |
| **Music API** | Piped (YouTube Music proxy) |
| **Lyrics** | LRCLIB (free synced lyrics) |
| **DI** | Hilt + KSP |
| **Network** | Retrofit + OkHttp |
| **Storage** | Room + DataStore |
| **Images** | Coil |
| **CI/CD** | GitHub Actions |

---

## 🏛️ Project Structure

```
com.musicify.app/
├── data/
│   ├── api/            → API interfaces (Piped, LRCLIB)
│   ├── di/             → Hilt modules
│   ├── model/          → Data classes
│   ├── repository/     → Repository layer
│   ├── local/          → Room database
│   └── internal/       → Internal configurations
├── player/             → Media3 service
├── ui/
│   ├── screens/
│   │   ├── home/       → Trending & recommendations
│   │   ├── search/     → Search with debounce
│   │   ├── library/    → Playlists & favorites
│   │   ├── player/     → Now playing + lyrics
│   │   └── settings/   → Preferences
│   ├── components/     → Shared UI components
│   └── theme/          → Colors, typography, shapes
└── utils/              → Extensions & helpers
```

---

## 🎯 Release Strategy

| Type | Tag | Description |
|------|-----|-------------|
| 🧪 **Beta** | `v0.x.x-beta` | Testing phase — may contain bugs |
| 🎉 **New Release** | `v1.0.0` | First stable release |
| ⬆️ **New Update** | `v1.x.0` | Feature additions |
| 🔧 **Bug Fixing** | `v1.x.x` | Patch with specific fixes listed |

**Current: `BETA 0.1.0`**

See [CHANGELOG.md](CHANGELOG.md) for detailed version history.

---

## 🤝 Contributing

Contributions, issues and feature requests are welcome!

1. **Fork** this repository
2. **Create** your branch: `git checkout -b feature/my-feature`
3. **Commit** your changes: `git commit -m 'Add my feature'`
4. **Push** to your branch: `git push origin feature/my-feature`
5. **Open** a Pull Request

---

## 📄 License

```
Copyright © 2024 felixsmkid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License v3.0.
See the LICENSE file for details.
```

This project is licensed under [**GNU GPL v3.0**](LICENSE) — you are free to use, modify, and distribute this software under the same license terms.

---

## 🙏 Acknowledgments

- [Piped](https://github.com/TeamPiped/Piped) — Privacy-friendly YouTube proxy
- [LRCLIB](https://lrclib.net) — Free community-driven lyrics database
- [Metrolist](https://github.com/MetrolistGroup/Metrolist) — Architectural inspiration
- [Material Design 3](https://m3.material.io) — Design system

---

<div align="center">

**⭐ Star this repo if you find it useful!**

Made with ❤️ by [felixsmkid](https://github.com/felixsmkid)

</div>
