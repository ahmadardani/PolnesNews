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
import com.kelompok1.polnesnews.components.AdminBottomNav
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.model.User
import com.kelompok1.polnesnews.ui.admin.AddANewCategoryScreen // 🟢 Import
import com.kelompok1.polnesnews.ui.admin.AdminDashboardScreen
import com.kelompok1.polnesnews.ui.admin.ManageCategoriesScreen // 🟢 Import
import com.kelompok1.polnesnews.ui.admin.ManageNewsScreen
import com.kelompok1.polnesnews.ui.admin.ManageUsersScreen // 🟢 Import
import com.kelompok1.polnesnews.ui.user.SettingsScreen
import androidx.navigation.NavType // 👈 Wajib
import androidx.navigation.navArgument // 👈 Wajib
import com.kelompok1.polnesnews.ui.admin.AdminSettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminNavGraph(
    rootNavController: NavHostController,
    currentUser: User?,
    onLogout: () -> Unit
) {
    val adminNavController = rememberNavController()
    val context = LocalContext.current

    // Pantau route aktif
    val navBackStackEntry by adminNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "Dashboard"

    // Daftar layar utama yang menampilkan Bottom Bar & Top Bar Induk
    // "add_category" TIDAK masuk sini, karena dia punya Scaffold sendiri (pola Form)
    val adminMainScreens = listOf("Dashboard", "News", "Categories", "Users", "Settings")
    val showBars = currentRoute in adminMainScreens

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
                            popUpTo(adminNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
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
            // 1. DASHBOARD
            composable("Dashboard") {
                AdminDashboardScreen(
                    currentUser = currentUser
                )
            }

            // 2. MANAGE NEWS
            composable("News") {
                ManageNewsScreen()
            }

            // 3. MANAGE CATEGORIES (LIST)
            composable("Categories") {
                ManageCategoriesScreen(
                    onAddCategoryClick = {
                        // Navigasi ke halaman form tambah kategori
                        adminNavController.navigate("add_category")
                    }
                )
            }

            composable(
                route = "add_category?categoryId={categoryId}",
                arguments = listOf(
                    navArgument("categoryId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                val argId = backStackEntry.arguments?.getInt("categoryId") ?: -1
                val finalId = if (argId == -1) null else argId

                // 🟢 PERSIAPAN FRONT-END UNTUK OPSI A
                AddANewCategoryScreen(
                    categoryId = finalId,
                    onBackClick = { adminNavController.popBackStack() },
                    onSubmitClick = {
                        // TODO: Logic simpan (Nanti konek API)
                        Toast.makeText(context, "Category Saved!", Toast.LENGTH_SHORT).show()
                        adminNavController.popBackStack()
                    },
                    onDeleteClick = {
                        // 🟢 LOGIKA PROTEKSI (OPSI A)
                        if (finalId != null) {
                            // 1. Cek apakah kategori ini dipakai di berita manapun
                            val isCategoryUsed = com.kelompok1.polnesnews.model.DummyData.newsList.any { it.categoryId == finalId }

                            if (isCategoryUsed) {
                                // 🔴 KASUS TOLAK: Beri tahu user kenapa tidak bisa dihapus
                                Toast.makeText(
                                    context,
                                    "Gagal! Kategori ini masih digunakan oleh artikel lain.",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                // 🟢 KASUS BOLEH: Hapus (Simulasi)
                                Toast.makeText(context, "Category Deleted!", Toast.LENGTH_SHORT).show()
                                adminNavController.popBackStack()
                            }
                        }
                    }
                )
            }
            // 4. MANAGE USERS
            composable("Users") {
                ManageUsersScreen()
            }

            // 5. SETTINGS
            composable("Settings") {
                // ✅ PERBAIKAN: Panggil screen khusus Admin dan oper currentUser
                AdminSettingsScreen(
                    currentUser = currentUser, // Pastikan data user dioper ke sini
                    onLogout = onLogout
                )
            }
        }
    }
}