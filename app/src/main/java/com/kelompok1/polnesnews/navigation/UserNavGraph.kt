package com.kelompok1.polnesnews.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.kelompok1.polnesnews.utils.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNavGraph(
    rootNavController: NavHostController
) {
    val userNavController = rememberNavController()
    val navBackStackEntry by userNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "Home"

    val userScreens = listOf("Home", "Categories", "Notifications", "Settings")
    val showBars = userScreens.any { currentRoute.startsWith(it) }

    Scaffold(
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
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
        ) {
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

            // 🟢 BAGIAN PENTING: Menghubungkan klik Notifikasi ke Detail Berita
            composable("Notifications") {
                NotificationsScreen(
                    onNewsClick = { newsId ->
                        userNavController.navigate("NewsDetail/$newsId")
                    }
                )
            }

            composable("Settings") {
                SettingsScreen(
                    onLogout = {
                        SessionManager.currentUser = null
                        rootNavController.navigate("auth_graph") {
                            popUpTo("user_root") { inclusive = true }
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

            // Halaman Detail Berita (Tujuan Akhir)
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