package com.kelompok1.polnesnews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kelompok1.polnesnews.model.User // <-- TAMBAHKAN IMPORT INI
import com.kelompok1.polnesnews.ui.editor.EditorDashboardScreen
import com.kelompok1.polnesnews.ui.editor.EditorSettingsScreen
import com.kelompok1.polnesnews.ui.editor.YourArticleScreen

@Composable
fun EditorNavGraph(
    navController: NavHostController,
    currentUser: User?, // <-- 1. Terima data user yang sedang login
    onLogout: () -> Unit  // <-- 2. Terima aksi untuk logout

) {
    NavHost(
        navController = navController,
        startDestination = "editor_articles"
    ) {
        composable("editor_articles") {
            // YourArticleScreen butuh NavController
            YourArticleScreen(navController)
        }

        composable("editor_dashboard") {
            EditorDashboardScreen(
                editorId = currentUser?.id ?: -1,
                currentRoute = "editor_dashboard", // rute saat ini
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable("editor_settings") {
            // EditorSettingsScreen butuh semua ini
            EditorSettingsScreen(
                currentUser = currentUser,
                onLogout = onLogout, // <-- Teruskan aksi logout
                onNavigateToDashboard = {
                    navController.navigate("editor_dashboard")
                },
                onNavigateToMyArticles = {
                    navController.navigate("editor_articles")
                }
            )
        }
    }
}