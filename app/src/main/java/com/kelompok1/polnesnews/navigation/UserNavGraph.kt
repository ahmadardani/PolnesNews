package com.kelompok1.polnesnews.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun UserNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { Text("Home Screen (User)") }
        composable("recent_news") { Text("Recent News Screen") }
        composable("most_viewed") { Text("Most Viewed Screen") }
        composable("categories") { Text("Categories Screen") }
        composable("notifications") { Text("Notification Screen") }
        composable("settings") { Text("Settings Screen") }
    }
}
