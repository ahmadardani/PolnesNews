package com.kelompok1.polnesnews.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kelompok1.polnesnews.auth.LoginScreen
import com.kelompok1.polnesnews.auth.SignUpScreen
import com.kelompok1.polnesnews.auth.WelcomeScreen
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.UserRole
// 🔴 Import SessionManager DIHAPUS

@Composable
fun AuthNavGraph(
    rootNavController: NavHostController
) {
    val authNavController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = authNavController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                rootNavController = rootNavController,
                authNavController = authNavController
            )
        }

        composable("login") {
            LoginScreen(
                rootNavController = rootNavController,
                authNavController = authNavController,
                onLoginSubmitted = { emailInput, passwordInput ->

                    // 1. Verifikasi User (Masih menggunakan DummyData)
                    val matchedUser = DummyData.userList.find {
                        it.email == emailInput && it.password == passwordInput
                    }

                    if (matchedUser != null) {
                        // 🔴 2. HAPUS: SessionManager.currentUser = matchedUser

                        // 3. NAVIGASI HARUS SESUAI DENGAN PERAN (ROLE)
                        val destinationRoute = when (matchedUser.role) {
                            UserRole.ADMIN -> "admin_root"
                            UserRole.EDITOR -> "editor_root"
                            UserRole.USER -> "user_root"
                        }

                        // 🟢 Logika Login sukses
                        rootNavController.navigate(destinationRoute) {
                            // Hapus stack autentikasi agar tombol back tidak kembali ke login
                            popUpTo("auth_graph") { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        composable("signup") {
            SignUpScreen(
                rootNavController = rootNavController,
                authNavController = authNavController
            )
        }
    }
}