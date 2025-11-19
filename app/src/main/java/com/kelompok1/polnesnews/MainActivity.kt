package com.kelompok1.polnesnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge // 👈 1. WAJIB IMPORT INI
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kelompok1.polnesnews.navigation.AuthNavGraph
import com.kelompok1.polnesnews.navigation.EditorNavGraph
import com.kelompok1.polnesnews.navigation.UserNavGraph
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme
import com.kelompok1.polnesnews.utils.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 👈 2. TAMBAHKAN BARIS INI SEBELUM setContent
        // Ini membuat Status Bar & Nav Bar jadi transparan/menyatu
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
            val currentUser = SessionManager.currentUser
            EditorNavGraph(
                rootNavController = rootNavController,
                currentUser = currentUser,
                onLogout = {
                    SessionManager.currentUser = null
                    rootNavController.navigate("auth_graph") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}