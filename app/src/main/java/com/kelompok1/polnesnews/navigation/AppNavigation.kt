package com.kelompok1.polnesnews.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kelompok1.polnesnews.model.User
import com.kelompok1.polnesnews.utils.SessionManager

/**
 * ROOT NAVIGATION (Pengatur Lalu Lintas Utama)
 * File ini menyatukan Auth, User, Editor, dan Admin Graph.
 */
@Composable
fun AppNavigation() {
    val rootNavController = rememberNavController()

    // Cek sesi user saat aplikasi dibuka (biar kalau sudah login, langsung masuk)
    // Anggap SessionManager sudah kamu buat
    val currentUser = SessionManager.currentUser
    val startDestination = if (currentUser != null) {
        when (currentUser.role) {
            "ADMIN" -> "admin_root"
            "EDITOR" -> "editor_root"
            else -> "user_root"
        }
    } else {
        "auth_graph"
    }

    NavHost(
        navController = rootNavController,
        startDestination = startDestination,
        route = "root_graph" // ID untuk graph utama
    ) {

        // 1. RUTE AUTHENTICATION (Login/Register)
        composable("auth_graph") {
            AuthNavGraph(rootNavController = rootNavController)
        }

        // 2. RUTE USER BIASA
        composable("user_root") {
            // Kita panggil NavGraph User yang sudah dipisah tadi
            UserNavGraph(rootNavController = rootNavController)
        }

        // 3. RUTE EDITOR
        composable("editor_root") {
            EditorNavGraph(
                rootNavController = rootNavController,
                currentUser = SessionManager.currentUser,
                onLogout = {
                    SessionManager.currentUser = null
                    // Kembali ke Auth saat logout
                    rootNavController.navigate("auth_graph") {
                        popUpTo("root_graph") { inclusive = true }
                    }
                }
            )
        }

        // 4. RUTE ADMIN
        composable("admin_root") {
            AdminNavGraph(
                rootNavController = rootNavController,
                currentUser = SessionManager.currentUser,
                onLogout = {
                    SessionManager.currentUser = null
                    rootNavController.navigate("auth_graph") {
                        popUpTo("root_graph") { inclusive = true }
                    }
                }
            )
        }
    }
}