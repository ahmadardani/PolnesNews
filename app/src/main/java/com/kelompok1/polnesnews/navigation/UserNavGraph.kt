package com.kelompok1.polnesnews.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kelompok1.polnesnews.components.PolnesTopAppBar
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.components.UserBottomNav
import com.kelompok1.polnesnews.ui.user.*

// Impor untuk Animasi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
// Hapus slideInVertically / slideOutVertically

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNavGraph(
    rootNavController: NavHostController
) {
    val userNavController = rememberNavController()
    val navBackStackEntry by userNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "Home"

    // Daftar layar utama & indeksnya
    val userScreens = listOf("Home", "Categories", "Notifications", "Settings")
    val showBars = currentRoute in userScreens

    Scaffold(
        // ... (topBar dan bottomBar Anda tetap sama) ...
        topBar = {
            if (showBars) {
                when (currentRoute) {
                    "Home" -> PolnesTopAppBar()
                    "Categories" -> TitleOnlyTopAppBar(title = "Categories")
                    "Notifications" -> TitleOnlyTopAppBar(title = "Notifications")
                    "Settings" -> TitleOnlyTopAppBar(title = "Settings")
                }
            }
        },
        bottomBar = {
            if (showBars) {
                UserBottomNav(
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        userNavController.navigate(route) {
                            popUpTo(userNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = userNavController,
            startDestination = "Home",
            modifier = if (showBars) Modifier.padding(innerPadding) else Modifier,

            // --- REVISI LOGIKA ANIMASI ---

            enterTransition = {
                val initialRoute = initialState.destination.route
                val targetRoute = targetState.destination.route

                // 1. Perpindahan antar tab bottom nav
                if (initialRoute in userScreens && targetRoute in userScreens) {
                    val initialIndex = userScreens.indexOf(initialRoute)
                    val targetIndex = userScreens.indexOf(targetRoute)

                    if (targetIndex > initialIndex) {
                        slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300))
                    } else {
                        slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300))
                    }
                }
                // 2. "Sisanya": Masuk ke layar detail
                else {
                    // GANTI DARI VERTIKAL KE HORIZONTAL
                    // Masuk dari KANAN (efek push)
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(300)
                    )
                }
            },
            exitTransition = {
                val initialRoute = initialState.destination.route
                val targetRoute = targetState.destination.route

                // 1. Perpindahan antar tab bottom nav
                if (initialRoute in userScreens && targetRoute in userScreens) {
                    val initialIndex = userScreens.indexOf(initialRoute)
                    val targetIndex = userScreens.indexOf(targetRoute)

                    if (targetIndex > initialIndex) {
                        slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300))
                    } else {
                        slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300))
                    }
                }
                // 2. "Sisanya": Keluar dari layar utama
                else {
                    // GANTI DARI VERTIKAL KE HORIZONTAL
                    // Keluar ke KIRI
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(300)
                    )
                }
            },

            // Animasi 'Pop' (tombol Back) sudah benar (geser dari/ke samping)
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300))
            }
            // --- BATAS REVISI ANIMASI ---

        ) {
            // ... (Semua composable() Anda tetap sama) ...
            composable("Home") {
                HomeScreen(
                    onViewAllRecent = { userNavController.navigate("RecentNews") },
                    onViewAllMostViewed = { userNavController.navigate("MostViewedNews") },
                    onNewsClick = { newsId -> userNavController.navigate("NewsDetail/$newsId") }
                )
            }
            composable("Categories") {
                CategoriesScreen(
                    onCategoryClick = { categoryName ->
                        userNavController.navigate("CategorySelected/$categoryName")
                    }
                )
            }
            composable("Notifications") { NotificationsScreen() }
            composable("Settings") {
                SettingsScreen(
                    onLogout = {
                        rootNavController.navigate("auth") {
                            popUpTo("user_app") { inclusive = true }
                        }
                    }
                )
            }
            composable(
                route = "CategorySelected/{categoryName}",
                arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                CategorySelectedScreen(
                    categoryName = categoryName,
                    onNavigateBack = { userNavController.popBackStack() },
                    onNewsClick = { newsId -> userNavController.navigate("NewsDetail/$newsId") }
                )
            }
            composable("RecentNews") {
                RecentNewsScreen(
                    onNavigateBack = { userNavController.popBackStack() },
                    onNewsClick = { newsId -> userNavController.navigate("NewsDetail/$newsId") }
                )
            }
            composable("MostViewedNews") {
                MostViewedNewsScreen(
                    onNavigateBack = { userNavController.popBackStack() },
                    onNewsClick = { newsId -> userNavController.navigate("NewsDetail/$newsId") }
                )
            }
            composable(
                route = "NewsDetail/{newsId}",
                arguments = listOf(navArgument("newsId") { type = NavType.IntType })
            ) { backStackEntry ->
                val newsId = backStackEntry.arguments?.getInt("newsId") ?: 0
                NewsDetailScreen(
                    onNavigateBack = { userNavController.popBackStack() },
                    newsId = newsId
                )
            }
        }
    }
}