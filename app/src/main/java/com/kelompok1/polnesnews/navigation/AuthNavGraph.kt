package com.kelompok1.polnesnews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kelompok1.polnesnews.auth.WelcomeScreen
import com.kelompok1.polnesnews.auth.LoginScreen
import com.kelompok1.polnesnews.auth.SignUpScreen

/**
 * Ini adalah Navigation Graph terpisah (nested graph) yang khusus menangani
 * semua layar yang berhubungan dengan otentikasi (Welcome, Login, Sign Up).
 */
@Composable
fun AuthNavGraph(
    // Ini adalah NavController utama ('root') dari aplikasi (misal, dari MainActivity).
    // Fungsinya untuk navigasi KELUAR dari alur otentikasi ini
    // (Contoh: setelah login sukses, navigasi ke 'home' pakai controller ini).
    rootNavController: NavHostController
) {
    // Buat NavController baru (lokal) yang HANYA akan mengatur
    // navigasi DI DALAM alur otentikasi ini.
    // (Contoh: dari 'welcome' ke 'login', atau 'login' ke 'signup').
    val authNavController = rememberNavController()

    // NavHost ini menggunakan 'authNavController' (lokal)
    NavHost(
        navController = authNavController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                // Diteruskan untuk navigasi KELUAR (jika diperlukan)
                rootNavController = rootNavController,
                // Diteruskan untuk navigasi INTERNAL (ke 'login' atau 'signup')
                authNavController = authNavController
            )
        }
        composable("login") {
            LoginScreen(
                // Diteruskan untuk navigasi KELUAR (setelah login sukses)
                rootNavController = rootNavController,
                // Diteruskan untuk navigasi INTERNAL (misal, kembali ke 'welcome')
                authNavController = authNavController
            )
        }
        composable("signup") {
            SignUpScreen(
                // Diteruskan untuk navigasi KELUAR (setelah daftar sukses)
                rootNavController = rootNavController,
                // Diteruskan untuk navigasi INTERNAL (misal, kembali ke 'welcome')
                authNavController = authNavController
            )
        }
    }
}