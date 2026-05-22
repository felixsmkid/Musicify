package com.musicify.app.ui.screens.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun LibraryScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your Library",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            IconButton(onClick = { }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Create playlist",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                LibraryItem(
                    icon = Icons.Default.Favorite,
                    title = "Liked Songs",
                    subtitle = "0 songs"
                )
            }
            item {
                LibraryItem(
                    icon = Icons.Default.History,
                    title = "Recently Played",
                    subtitle = "Your listening history"
                )
            }
            item {
                LibraryItem(
                    icon = Icons.Default.PlaylistPlay,
                    title = "Playlists",
                    subtitle = "Your playlists"
                )
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Your library is empty",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Start listening to build your library",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LibraryItem(icon: ImageVector, title: String, subtitle: String) {
    ListItem(
        headlineContent = {
            Text(text = title, color = MaterialTheme.colorScheme.onSurface)
        },
        supportingContent = {
            Text(text = subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
        },
        leadingContent = {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        icon,
                        contentDescription = title,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    )
}
