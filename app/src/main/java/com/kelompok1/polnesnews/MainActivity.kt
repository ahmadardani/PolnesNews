package com.kelompok1.polnesnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kelompok1.polnesnews.navigation.AdminNavGraph
import com.kelompok1.polnesnews.navigation.AuthNavGraph
import com.kelompok1.polnesnews.navigation.EditorNavGraph
import com.kelompok1.polnesnews.navigation.UserNavGraph
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme
// 🔴 Import SessionManager DIHAPUS

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PolnesNewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppRootNavigation()
                }
            }
        }
    }
}

@Composable
fun AppRootNavigation() {
    val rootNavController = rememberNavController()

    // 🟢 Logika logout yang berulang diekstrak menjadi satu fungsi lambda
    val logoutAction: () -> Unit = {
        // Hapus stack navigasi sebelumnya dan kembali ke Login (Auth Graph)
        rootNavController.navigate("auth_graph") {
            popUpTo(0) { inclusive = true }
        }
    }

    NavHost(
        navController = rootNavController,
        startDestination = "auth_graph"
    ) {
        // 1. LOGIN
        composable("auth_graph") {
            AuthNavGraph(rootNavController = rootNavController)
        }

        // 2. USER BIASA
        composable("user_root") {
            UserNavGraph(rootNavController = rootNavController)
        }

        // 3. EDITOR
        composable("editor_root") {
            // 🔴 Hapus: val currentUser = SessionManager.currentUser
            EditorNavGraph(
                rootNavController = rootNavController,
                // 🔴 Hapus parameter currentUser
                onLogout = logoutAction // 🟢 Gunakan fungsi logout yang sudah disederhanakan
            )
        }

        // 4. ADMIN
        composable("admin_root") {
            // 🔴 Hapus: val currentUser = SessionManager.currentUser
            AdminNavGraph(
                rootNavController = rootNavController,
                // 🔴 Hapus parameter currentUser
                onLogout = logoutAction // 🟢 Gunakan fungsi logout yang sudah disederhanakan
            )
        }
    }
}