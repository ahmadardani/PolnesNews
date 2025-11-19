package com.kelompok1.polnesnews.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kelompok1.polnesnews.components.EditorBottomNav
import com.kelompok1.polnesnews.model.User
import com.kelompok1.polnesnews.ui.editor.EditorDashboardScreen
import com.kelompok1.polnesnews.ui.editor.EditorSettingsScreen
import com.kelompok1.polnesnews.ui.editor.YourArticleScreen
import com.kelompok1.polnesnews.ui.screen.AddANewArticleScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorNavGraph(
    rootNavController: NavHostController,
    currentUser: User?,
    onLogout: () -> Unit
) {
    val editorNavController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        // 🔴 PERBAIKAN: topBar DIHAPUS dari sini (biar tidak dobel)

        // ✅ HANYA bottomBar SAJA
        bottomBar = {
            EditorBottomNav(
                currentRoute = "editor_articles", // (Opsional: Nanti bisa dibuat dinamis)
                onNavigate = { route ->
                    editorNavController.navigate(route) {
                        popUpTo(editorNavController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = editorNavController,
            startDestination = "editor_articles",
            modifier = Modifier.padding(innerPadding)
        ) {
            // 1. DASHBOARD
            composable("editor_dashboard") {
                EditorDashboardScreen(
                    editorId = currentUser?.id ?: -1,
                    currentRoute = "editor_dashboard",
                    onNavigate = { route ->
                        editorNavController.navigate(route) {
                            popUpTo(editorNavController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }

            // 2. YOUR ARTICLES
            composable("editor_articles") {
                YourArticleScreen(navController = editorNavController)
            }

            // 3. SETTINGS
            composable("editor_settings") {
                EditorSettingsScreen(
                    navController = editorNavController,
                    currentUser = currentUser,
                    onLogout = onLogout
                )
            }

            // 4. ADD/EDIT FORM
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