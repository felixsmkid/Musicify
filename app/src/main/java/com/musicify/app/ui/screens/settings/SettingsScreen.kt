package com.musicify.app.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(16.dp)
            )
        }

        item { SettingsCategory("Playback") }
        item {
            SettingsItem(
                icon = Icons.Default.HighQuality,
                title = "Audio Quality",
                subtitle = "High (320kbps)"
            )
        }
        item {
            SettingsToggle(
                icon = Icons.Default.SkipNext,
                title = "Skip Silence",
                subtitle = "Automatically skip silent parts"
            )
        }
        item {
            SettingsToggle(
                icon = Icons.Default.VolumeUp,
                title = "Audio Normalization",
                subtitle = "Normalize volume across tracks"
            )
        }

        item { SettingsCategory("Appearance") }
        item {
            SettingsItem(
                icon = Icons.Default.DarkMode,
                title = "Theme",
                subtitle = "System default"
            )
        }
        item {
            SettingsToggle(
                icon = Icons.Default.Palette,
                title = "Dynamic Colors",
                subtitle = "Use Material You colors"
            )
        }

        item { SettingsCategory("Lyrics") }
        item {
            SettingsToggle(
                icon = Icons.Default.Lyrics,
                title = "Show Lyrics",
                subtitle = "Display synced lyrics during playback"
            )
        }
        item {
            SettingsItem(
                icon = Icons.Default.TextFields,
                title = "Lyrics Font Size",
                subtitle = "Medium"
            )
        }

        item { SettingsCategory("About") }
        item {
            SettingsItem(
                icon = Icons.Default.Info,
                title = "Version",
                subtitle = "BETA 0.1.0"
            )
        }
        item {
            SettingsItem(
                icon = Icons.Default.Code,
                title = "Source Code",
                subtitle = "github.com/felixsmkid/Musicify"
            )
        }
    }
}

@Composable
fun SettingsCategory(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    ListItem(
        headlineContent = { Text(title, color = MaterialTheme.colorScheme.onSurface) },
        supportingContent = { Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        leadingContent = {
            Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    )
}

@Composable
fun SettingsToggle(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    var checked by remember { mutableStateOf(false) }
    ListItem(
        headlineContent = { Text(title, color = MaterialTheme.colorScheme.onSurface) },
        supportingContent = { Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        leadingContent = {
            Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        },
        trailingContent = {
            Switch(checked = checked, onCheckedChange = { checked = it })
        }
    )
}
