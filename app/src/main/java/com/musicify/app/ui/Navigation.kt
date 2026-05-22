package com.musicify.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.musicify.app.ui.screens.auth.AuthScreen
import com.musicify.app.ui.screens.home.HomeScreen
import com.musicify.app.ui.screens.library.LibraryScreen
import com.musicify.app.ui.screens.onboarding.OnboardingScreen
import com.musicify.app.ui.screens.onboarding.WelcomeViewModel
import com.musicify.app.ui.screens.search.SearchScreen
import com.musicify.app.ui.screens.settings.SettingsScreen

sealed class Screen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Home : Screen("home", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    data object Search : Screen("search", "Search", Icons.Filled.Search, Icons.Outlined.Search)
    data object Library : Screen("library", "Library", Icons.Filled.LibraryMusic, Icons.Outlined.LibraryMusic)
    data object Settings : Screen("settings", "Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
}

@Composable
fun MusicifyNavHost() {
    val welcomeViewModel: WelcomeViewModel = hiltViewModel()
    val showOnboarding by welcomeViewModel.showOnboarding.collectAsState()
    var showAuth by remember { mutableStateOf(false) }
    var setupComplete by remember { mutableStateOf(false) }

    LaunchedEffect(showOnboarding) {
        if (!showOnboarding) {
            setupComplete = true
        }
    }

    when {
        showOnboarding && !setupComplete -> {
            OnboardingScreen(
                onComplete = {
                    welcomeViewModel.completeOnboarding()
                    showAuth = true
                }
            )
        }
        showAuth -> {
            AuthScreen(
                onSignInSuccess = { _, _, _ -> showAuth = false },
                onSkip = { showAuth = false }
            )
        }
        else -> {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val screens = listOf(Screen.Home, Screen.Search, Screen.Library, Screen.Settings)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 0.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                screens.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
                                contentDescription = screen.title
                            )
                        },
                        label = { Text(screen.title, style = MaterialTheme.typography.labelSmall) },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Search.route) { SearchScreen() }
            composable(Screen.Library.route) { LibraryScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}
