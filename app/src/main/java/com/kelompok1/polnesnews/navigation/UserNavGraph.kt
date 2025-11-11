package com.kelompok1.polnesnews.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kelompok1.polnesnews.components.PolnesTopAppBar // (Nama ini sepertinya custom)
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.components.UserBottomNav
import com.kelompok1.polnesnews.ui.user.* // Impor semua layar user

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNavGraph(
    // Menerima NavController 'root' dari MainActivity.
    // Ini dipakai untuk navigasi KELUAR dari graph ini (contoh: untuk Logout).
    rootNavController: NavHostController
) {
    // Membuat NavController internal (lokal) HANYA untuk layar-layar
    // di dalam 'user flow' (Home, Settings, Detail, dll).
    val userNavController = rememberNavController()

    // Kita perlu 'listen' ke back stack untuk tahu kita sedang di layar mana.
    val navBackStackEntry by userNavController.currentBackStackEntryAsState()
    // Ambil nama rute yang aktif saat ini, default-nya "Home".
    val currentRoute = navBackStackEntry?.destination?.route ?: "Home"

    // --- LOGIKA PENTING UNTUK UI ---
    // Tentukan layar mana saja yang boleh menampilkan TopBar dan BottomBar.
    val userScreens = listOf("Home", "Categories", "Notifications", "Settings")
    // 'showBars' akan bernilai 'true' HANYA jika 'currentRoute' ada di dalam list di atas.
    // Ini berarti layar seperti "NewsDetail" tidak akan menampilkannya.
    val showBars = currentRoute in userScreens

    Scaffold(
        topBar = {
            // Tampilkan TopBar yang berbeda-beda tergantung layar,
            // tapi hanya jika 'showBars' bernilai 'true'.
            if (showBars) {
                when (currentRoute) {
                    "Home" -> PolnesTopAppBar() // TopBar khusus untuk Home
                    "Categories" -> TitleOnlyTopAppBar(title = "Categories")
                    "Notifications" -> TitleOnlyTopAppBar(title = "Notifications")
                    "Settings" -> TitleOnlyTopAppBar(title = "Settings")
                }
            }
        },
        bottomBar = {
            // Tampilkan BottomNav HANYA jika kita ada di salah satu layar utama.
            if (showBars) {
                UserBottomNav(
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        // Ini adalah logika navigasi standar untuk BottomBar
                        userNavController.navigate(route) {
                            // Pop up ke start destination (Home) untuk menghindari back stack yang menumpuk
                            popUpTo(userNavController.graph.findStartDestination().id) {
                                saveState = true // Simpan state layar sebelumnya
                            }
                            // Hindari membuka layar yang sama berulang kali
                            launchSingleTop = true
                            // Pulihkan state jika kita kembali ke layar yang sudah dibuka
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        // NavHost ini adalah 'jantung' dari navigasi internal user
        NavHost(
            navController = userNavController, // PENTING: Pakai userNavController (lokal)
            startDestination = "Home",
            // Terapkan padding dari Scaffold, TAPI hanya jika bar-nya tampil.
            // Jika tidak, layar detail (seperti NewsDetail) akan punya padding kosong di atas.
            modifier = if (showBars) Modifier.padding(innerPadding) else Modifier
        ) {

            // --- Definisi Layar-Layar Utama (dengan BottomBar) ---
            composable("Home") {
                HomeScreen(
                    // Navigasi internal pakai 'userNavController'
                    onViewAllRecent = { userNavController.navigate("RecentNews") },
                    onViewAllMostViewed = { userNavController.navigate("MostViewedNews") },
                    onNewsClick = { newsId -> userNavController.navigate("NewsDetail/$newsId") }
                )
            }
            composable("Categories") { CategoriesScreen() }
            composable("Notifications") { NotificationsScreen() }
            composable("Settings") {
                SettingsScreen(
                    onLogout = {
                        // PENTING: Untuk Logout, kita pakai 'rootNavController'
                        // untuk 'kabur' dari 'user_app' dan kembali ke 'auth'.
                        rootNavController.navigate("auth") {
                            popUpTo("user_app") { inclusive = true } // Hapus 'user_app' dari back stack
                        }
                    }
                )
            }

            // --- Definisi Layar-Layar 'Detail' (tanpa BottomBar) ---
            composable("RecentNews") {
                RecentNewsScreen(
                    onNavigateBack = { userNavController.popBackStack() }, // Fungsi 'back' standar
                    onNewsClick = { newsId -> userNavController.navigate("NewsDetail/$newsId") }
                )
            }
            composable("MostViewedNews") {
                MostViewedNewsScreen(
                    onNavigateBack = { userNavController.popBackStack() },
                    onNewsClick = { newsId -> userNavController.navigate("NewsDetail/$newsId") }
                )
            }

            // Layar detail berita dengan argumen 'newsId'
            composable(
                route = "NewsDetail/{newsId}",
                arguments = listOf(navArgument("newsId") { type = NavType.IntType })
            ) { backStackEntry ->
                // Ambil ID berita dari argumen navigasi
                val newsId = backStackEntry.arguments?.getInt("newsId") ?: 0
                NewsDetailScreen(
                    onNavigateBack = { userNavController.popBackStack() },
                    newsId = newsId
                )
            }
        }
    }
}