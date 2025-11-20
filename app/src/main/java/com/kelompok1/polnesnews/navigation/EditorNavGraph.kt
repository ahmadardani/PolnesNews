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
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar // Asumsi ini komponenmu
import com.kelompok1.polnesnews.model.User
import com.kelompok1.polnesnews.ui.editor.EditorDashboardScreen
import com.kelompok1.polnesnews.ui.editor.EditorSettingsScreen
import com.kelompok1.polnesnews.ui.editor.YourArticleScreen
import com.kelompok1.polnesnews.ui.editor.AddANewArticleScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorNavGraph(
    rootNavController: NavHostController,
    currentUser: User?,
    onLogout: () -> Unit
) {
    val editorNavController = rememberNavController()
    val context = LocalContext.current

    // 1. Mendapatkan route aktif secara real-time
    val navBackStackEntry by editorNavController.currentBackStackEntryAsState()
    val fullRoute = navBackStackEntry?.destination?.route
    val currentRoute = fullRoute?.substringBefore("?") ?: "editor_dashboard"

    // Tentukan menu yang menggunakan Bottom Bar & Top Bar sederhana
    val mainRoutes = listOf("editor_dashboard", "editor_articles", "editor_settings")
    val showMainBars = currentRoute in mainRoutes

    // Dapatkan judul halaman
    val title = getScreenTitle(currentRoute)

    Scaffold(
        // 🟢 PERBAIKAN TOP BAR: Dynamic TopBar di Induk Scaffold
        topBar = {
            if (showMainBars) {
                // Top Bar akan tampil jika bukan halaman Form (Add/Edit)
                TitleOnlyTopAppBar(title = title)
            }
        },

        // ✅ BottomBar (Sudah dinamis)
        bottomBar = {
            if (showMainBars) {
                EditorBottomNav(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        editorNavController.navigate(route) {
                            popUpTo(editorNavController.graph.startDestinationId) {
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
            navController = editorNavController,
            startDestination = "editor_dashboard",
            // 💡 PENTING: Padding dari Scaffold Induk diterapkan di sini
            modifier = Modifier.padding(innerPadding)
        ) {
            // 1. DASHBOARD (CONTENT-ONLY)
            composable("editor_dashboard") {
                // Panggil composable tanpa Scaffold/TopBar
                EditorDashboardScreen(
                    editorId = currentUser?.id ?: -1,
                    currentRoute = "editor_dashboard",
                    onNavigate = { /* ... */ }
                )
            }

            // 2. YOUR ARTICLES (CONTENT-ONLY)
            composable("editor_articles") {
                YourArticleScreen(navController = editorNavController)
            }

            // 3. SETTINGS (CONTENT-ONLY)
            composable("editor_settings") {
                EditorSettingsScreen(
                    navController = editorNavController,
                    currentUser = currentUser,
                    onLogout = onLogout
                )
            }

            // 4. ADD/EDIT FORM (HARUS PUNYA SCAFFOLD SENDIRI UNTUK TOMBOL BACK/SAVE)
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

// Helper function untuk judul Top Bar
private fun getScreenTitle(route: String): String {
    return when (route) {
        "editor_dashboard" -> "Dashboard"
        "editor_articles" -> "Your Articles"
        "editor_settings" -> "Settings"
        else -> ""
    }
}