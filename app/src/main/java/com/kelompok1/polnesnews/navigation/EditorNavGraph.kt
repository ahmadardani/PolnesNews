package com.kelompok1.polnesnews.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kelompok1.polnesnews.components.EditorBottomNav
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.model.User
import com.kelompok1.polnesnews.ui.editor.EditorDashboardScreen
import com.kelompok1.polnesnews.ui.editor.EditorSettingsScreen
import com.kelompok1.polnesnews.ui.editor.YourArticleScreen
import com.kelompok1.polnesnews.ui.editor.AddANewArticleScreen
// 🟢 Import Layar Umum
import com.kelompok1.polnesnews.ui.common.PrivacyPolicyScreen
import com.kelompok1.polnesnews.ui.common.AboutScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorNavGraph(
    rootNavController: NavHostController,
    currentUser: User?,
    onLogout: () -> Unit
) {
    val editorNavController = rememberNavController()
    val context = LocalContext.current

    val navBackStackEntry by editorNavController.currentBackStackEntryAsState()
    val fullRoute = navBackStackEntry?.destination?.route
    val currentRoute = fullRoute?.substringBefore("?") ?: "editor_dashboard"

    val mainRoutes = listOf("editor_dashboard", "editor_articles", "editor_settings")
    // Privacy & About TIDAK dimasukkan sini, biar mereka punya TopBar sendiri (CommonTopBar)
    val showMainBars = currentRoute in mainRoutes

    val title = getScreenTitle(currentRoute)

    Scaffold(
        topBar = {
            if (showMainBars) {
                TitleOnlyTopAppBar(title = title)
            }
        },
        bottomBar = {
            if (showMainBars) {
                EditorBottomNav(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        editorNavController.navigate(route) {
                            popUpTo(editorNavController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = editorNavController,
            startDestination = "editor_dashboard",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("editor_dashboard") {
                EditorDashboardScreen(
                    editorId = currentUser?.id ?: -1,
                    currentRoute = "editor_dashboard",
                    onNavigate = { /* ... */ }
                )
            }

            composable("editor_articles") {
                YourArticleScreen(navController = editorNavController)
            }

            // 🟢 UPDATE SETTINGS
            composable("editor_settings") {
                EditorSettingsScreen(
                    navController = editorNavController,
                    // currentUser dihapus, pakai SessionManager di dalam screen
                    onLogout = onLogout,
                    onPrivacyClick = { editorNavController.navigate("PrivacyPolicy") }, // Navigasi
                    onAboutClick = { editorNavController.navigate("About") }          // Navigasi
                )
            }

            // 🟢 ROUTE BARU: PRIVACY POLICY
            composable("PrivacyPolicy") {
                PrivacyPolicyScreen(
                    onNavigateBack = { editorNavController.popBackStack() }
                )
            }

            // 🟢 ROUTE BARU: ABOUT
            composable("About") {
                AboutScreen(
                    onNavigateBack = { editorNavController.popBackStack() }
                )
            }

            composable(
                route = "article_form?articleId={articleId}",
                arguments = listOf(navArgument("articleId") { type = NavType.IntType; defaultValue = -1 })
            ) { backStackEntry ->
                val argId = backStackEntry.arguments?.getInt("articleId") ?: -1
                val finalArticleId = if (argId == -1) null else argId

                AddANewArticleScreen(
                    articleId = finalArticleId,
                    onBackClick = { editorNavController.popBackStack() },
                    onSubmitClick = {
                        Toast.makeText(context, "Article Submitted!", Toast.LENGTH_SHORT).show()
                        editorNavController.popBackStack()
                    },
                    onDeleteClick = {
                        Toast.makeText(context, "Article Deleted!", Toast.LENGTH_SHORT).show()
                        editorNavController.popBackStack()
                    }
                )
            }
        }
    }
}

private fun getScreenTitle(route: String): String {
    return when (route) {
        "editor_dashboard" -> "Dashboard"
        "editor_articles" -> "Your Articles"
        "editor_settings" -> "Settings"
        else -> ""
    }
}