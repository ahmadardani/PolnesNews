package com.kelompok1.polnesnews.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.kelompok1.polnesnews.components.AdminBottomNav
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.model.User
import com.kelompok1.polnesnews.ui.admin.*
// 🟢 Import Layar Umum
import com.kelompok1.polnesnews.ui.common.PrivacyPolicyScreen
import com.kelompok1.polnesnews.ui.common.AboutScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminNavGraph(
    rootNavController: NavHostController,
    currentUser: User?,
    onLogout: () -> Unit
) {
    val adminNavController = rememberNavController()
    val context = LocalContext.current

    val navBackStackEntry by adminNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "Dashboard"

    val adminMainScreens = listOf("Dashboard", "News", "Categories", "Users", "Settings")

    // Pastikan Privacy & About TIDAK menampilkan TopBar/BottomBar dari Scaffold Admin ini
    // Karena mereka sudah punya CommonTopBar sendiri
    val showBars = adminMainScreens.any { currentRoute.startsWith(it) } &&
            !currentRoute.startsWith("add_category") &&
            !currentRoute.startsWith("edit_article")

    val topBarTitle = when (currentRoute) {
        "Dashboard" -> "Admin Dashboard"
        "News" -> "Manage News"
        "Categories" -> "Manage Categories"
        "Users" -> "Manage Users"
        "Settings" -> "Settings"
        else -> ""
    }

    Scaffold(
        topBar = {
            if (showBars) {
                TitleOnlyTopAppBar(title = topBarTitle)
            }
        },
        bottomBar = {
            if (showBars) {
                AdminBottomNav(
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        adminNavController.navigate(route) {
                            popUpTo(adminNavController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = adminNavController,
            startDestination = "Dashboard",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Dashboard") { AdminDashboardScreen(currentUser = currentUser) }

            composable("News") {
                ManageNewsScreen(
                    onEditArticleClick = { articleId -> adminNavController.navigate("edit_article/$articleId") }
                )
            }

            composable("edit_article/{articleId}", arguments = listOf(navArgument("articleId") { type = NavType.IntType })) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getInt("articleId") ?: -1
                AdminEditArticleScreen(
                    articleId = articleId,
                    onBackClick = { adminNavController.popBackStack() },
                    onSaveClick = {
                        Toast.makeText(context, "Article Updated Successfully!", Toast.LENGTH_SHORT).show()
                        adminNavController.popBackStack()
                    }
                )
            }

            composable("Categories") {
                ManageCategoriesScreen(
                    onAddCategoryClick = { adminNavController.navigate("add_category") },
                    onEditCategoryClick = { categoryId -> adminNavController.navigate("add_category?categoryId=$categoryId") }
                )
            }

            composable("add_category?categoryId={categoryId}", arguments = listOf(navArgument("categoryId") { type = NavType.IntType; defaultValue = -1 })) { backStackEntry ->
                val argId = backStackEntry.arguments?.getInt("categoryId") ?: -1
                val finalId = if (argId == -1) null else argId
                AddANewCategoryScreen(
                    categoryId = finalId,
                    onBackClick = { adminNavController.popBackStack() },
                    onSubmitClick = {
                        val msg = if (finalId == null) "Category Added!" else "Category Updated!"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        adminNavController.popBackStack()
                    }
                )
            }

            composable("Users") { ManageUsersScreen() }

            // 🟢 UPDATE SETTINGS
            composable("Settings") {
                AdminSettingsScreen(
                    // currentUser dihapus
                    onLogout = onLogout,
                    onPrivacyClick = { adminNavController.navigate("PrivacyPolicy") }, // Navigasi
                    onAboutClick = { adminNavController.navigate("About") }          // Navigasi
                )
            }

            // 🟢 ROUTE BARU: PRIVACY POLICY
            composable("PrivacyPolicy") {
                PrivacyPolicyScreen(
                    onNavigateBack = { adminNavController.popBackStack() }
                )
            }

            // 🟢 ROUTE BARU: ABOUT
            composable("About") {
                AboutScreen(
                    onNavigateBack = { adminNavController.popBackStack() }
                )
            }
        }
    }
}