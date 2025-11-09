package com.kelompok1.polnesnews.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AdminNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "manage_articles"
    ) {
        composable("manage_articles") { Text("Manage Articles Screen") }
        composable("add_article") { Text("Add Article Screen") }
        composable("edit_article") { Text("✏Edit Article Screen") }
        composable("manage_categories") { Text("Manage Categories Screen") }
        composable("rating_comments") { Text("Rating Comments Screen") }
        composable("settings") { Text("Settings Screen (Admin)") }
    }
}
