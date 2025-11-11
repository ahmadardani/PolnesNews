package com.kelompok1.polnesnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kelompok1.polnesnews.navigation.AuthNavGraph
import com.kelompok1.polnesnews.navigation.UserNavGraph
// import com.kelompok1.polnesnews.navigation.AdminNavGraph
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolnesNewsTheme {
                // Ini adalah NavController UTAMA (Root)
                val rootNavController = rememberNavController()

                // Ini adalah NavHost UTAMA (Root)
                NavHost(
                    navController = rootNavController,
                    startDestination = "auth" // Mulai dari Gerbang Otentikasi
                ) {

                    // Rute 1: Gerbang Otorisasi (Lobi)
                    // Rute "auth" akan memanggil AuthNavGraph
                    composable("auth") {
                        AuthNavGraph(rootNavController)
                    }

                    // Rute 2: Aplikasi User (Kantor Utama)
                    // Rute "user_app" akan memanggil UserNavGraph
                    composable("user_app") {
                        UserNavGraph(rootNavController)
                    }

                    // Rute 3: Aplikasi Admin (Ruang Kontrol)
                    // composable("admin_app") {
                    //     AdminNavGraph(rootNavController)
                    // }
                }
            }
        }
    }
}