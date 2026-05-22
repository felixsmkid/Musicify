package com.musicify.app.ui.screens.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
    ) {
        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }

        // Audio section
        item { SectionHeader("Audio") }
        item {
            SettingsItem(
                icon = Icons.Outlined.HighQuality,
                title = "Streaming Quality",
                subtitle = "High (320kbps)",
                onClick = {}
            )
        }
        item {
            var skipSilence by remember { mutableStateOf(false) }
            SettingsToggleItem(
                icon = Icons.Outlined.Speed,
                title = "Skip Silence",
                subtitle = "Auto-skip silent sections in tracks",
                checked = skipSilence,
                onToggle = { skipSilence = it }
            )
        }
        item {
            var normalize by remember { mutableStateOf(true) }
            SettingsToggleItem(
                icon = Icons.Outlined.VolumeUp,
                title = "Normalize Volume",
                subtitle = "Consistent volume across all tracks",
                checked = normalize,
                onToggle = { normalize = it }
            )
        }

        // Appearance section
        item { SectionHeader("Appearance") }
        item {
            SettingsItem(
                icon = Icons.Outlined.Palette,
                title = "Theme",
                subtitle = "Follow system",
                onClick = {}
            )
        }
        item {
            var dynamicColor by remember { mutableStateOf(true) }
            SettingsToggleItem(
                icon = Icons.Outlined.ColorLens,
                title = "Dynamic Colors",
                subtitle = "Extract colors from album artwork",
                checked = dynamicColor,
                onToggle = { dynamicColor = it }
            )
        }

        // Lyrics section
        item { SectionHeader("Lyrics") }
        item {
            var showLyrics by remember { mutableStateOf(true) }
            SettingsToggleItem(
                icon = Icons.Outlined.Lyrics,
                title = "Show Lyrics",
                subtitle = "Display synced lyrics during playback",
                checked = showLyrics,
                onToggle = { showLyrics = it }
            )
        }
        item {
            SettingsItem(
                icon = Icons.Outlined.TextFields,
                title = "Lyrics Size",
                subtitle = "Medium",
                onClick = {}
            )
        }

        // About section
        item { SectionHeader("About") }
        item {
            SettingsItem(
                icon = Icons.Outlined.Info,
                title = "Version",
                subtitle = "0.3.0-beta (build 3)",
                onClick = {}
            )
        }
        item {
            SettingsItem(
                icon = Icons.Outlined.Code,
                title = "Source Code",
                subtitle = "View on GitHub",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/felixsmkid/Musicify"))
                    context.startActivity(intent)
                }
            )
        }
        item {
            SettingsItem(
                icon = Icons.Outlined.BugReport,
                title = "Report Bug",
                subtitle = "Open an issue on GitHub",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/felixsmkid/Musicify/issues"))
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 20.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 2.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
