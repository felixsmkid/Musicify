package com.musicify.app.ui.screens.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import coil.compose.AsyncImage
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.musicify.app.data.internal.conf.ServiceRegistry
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var userName by remember { mutableStateOf<String?>(null) }
    var userEmail by remember { mutableStateOf<String?>(null) }
    var userPhoto by remember { mutableStateOf<String?>(null) }
    var isSigningIn by remember { mutableStateOf(false) }
    var signInError by remember { mutableStateOf<String?>(null) }

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

        // Account card at top
        item {
            AccountCard(
                userName = userName,
                userEmail = userEmail,
                userPhoto = userPhoto,
                isLoading = isSigningIn,
                error = signInError,
                onSignIn = {
                    isSigningIn = true
                    signInError = null
                    coroutineScope.launch {
                        try {
                            val credentialManager = CredentialManager.create(context)
                            val webClientId = ServiceRegistry.credential("gc_web")

                            val googleIdOption = GetGoogleIdOption.Builder()
                                .setFilterByAuthorizedAccounts(false)
                                .setServerClientId(webClientId)
                                .setAutoSelectEnabled(true)
                                .build()

                            val request = GetCredentialRequest.Builder()
                                .addCredentialOption(googleIdOption)
                                .build()

                            val result = credentialManager.getCredential(context, request)
                            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)

                            userName = googleIdTokenCredential.displayName ?: "User"
                            userEmail = googleIdTokenCredential.id
                            userPhoto = googleIdTokenCredential.profilePictureUri?.toString()
                            isSigningIn = false
                        } catch (e: Exception) {
                            isSigningIn = false
                            signInError = e.message ?: "Sign-in failed"
                        }
                    }
                }
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
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/felixsmkid/Musicify")))
                }
            )
        }
    }
}

@Composable
fun AccountCard(
    userName: String?,
    userEmail: String?,
    userPhoto: String?,
    isLoading: Boolean,
    error: String?,
    onSignIn: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF6C63FF).copy(alpha = 0.15f), Color(0xFFFF6584).copy(alpha = 0.1f))
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {
            if (userName != null) {
                // Logged in state
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (userPhoto != null) {
                        AsyncImage(
                            model = userPhoto,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF6C63FF),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = userName.first().uppercase(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = userEmail ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Connected to YouTube Music",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF4CAF50)
                        )
                    }
                }
            } else {
                // Not logged in
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xFF6C63FF)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Sign in with Google",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "Connect to sync your YouTube Music library",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    if (error != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = error,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onSignIn,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6C63FF)
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = if (isLoading) "Connecting..." else "Continue with Google",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
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
            Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f), modifier = Modifier.size(20.dp))
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
            Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Switch(checked = checked, onCheckedChange = onToggle, colors = SwitchDefaults.colors(checkedTrackColor = MaterialTheme.colorScheme.primary))
        }
    }
}
