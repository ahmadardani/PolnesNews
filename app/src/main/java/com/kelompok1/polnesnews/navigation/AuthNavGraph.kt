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
import com.kelompok1.polnesnews.utils.SessionManager

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

                    val matchedUser = DummyData.userList.find {
                        it.email == emailInput && it.password == passwordInput
                    }

                    if (matchedUser != null) {
                        SessionManager.currentUser = matchedUser

                        // NAVIGASI HARUS SESUAI DENGAN MAIN ACTIVITY
                        when (matchedUser.role) {
                            UserRole.EDITOR -> {
                                rootNavController.navigate("editor_root") {
                                    popUpTo("auth_graph") { inclusive = true }
                                }
                            }
                            UserRole.USER -> {
                                // Pastikan ini "user_root", BUKAN "user_app"
                                rootNavController.navigate("user_root") {
                                    popUpTo("auth_graph") { inclusive = true }
                                }
                            }
                            UserRole.ADMIN -> {
                                rootNavController.navigate("admin_root") {
                                    popUpTo("auth_graph") { inclusive = true }
                                }
                            }
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