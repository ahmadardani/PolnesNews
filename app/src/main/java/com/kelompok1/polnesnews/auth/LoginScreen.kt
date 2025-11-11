package com.kelompok1.polnesnews.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kelompok1.polnesnews.components.CommonTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    // Butuh 2 NavController:
    // 'authNavController' untuk navigasi di dalam flow auth (spt. ke register),
    // 'rootNavController' untuk navigasi keluar dari flow auth (ke halaman utama aplikasi).
    rootNavController: NavHostController,
    authNavController: NavController
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            // Pakai CommonTopBar yang reusable biar konsisten di semua layar
            CommonTopBar(
                title = "Login",
                onBack = { authNavController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Welcome back\nGlad to see you, Again!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                lineHeight = 40.sp
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Sembunyikan password" else "Tampilkan password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ClickableText(
                text = AnnotatedString("Forgot password?"),
                onClick = {
                    // TODO: Arahkan ke layar 'forgot_password'
                    // authNavController.navigate("forgot_password")
                },
                style = TextStyle(
                    color = Color(0xFF038900),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.End
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1.0f))

            Button(
                onClick = {
                    // TODO: Tambahkan logika validasi login dulu

                    // Jika berhasil, navigasi ke 'user_app' (halaman utama)
                    // dan hapus 'auth' dari back stack biar nggak bisa kembali ke login.
                    rootNavController.navigate("user_app") {
                        popUpTo("auth") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF038900),
                    contentColor = Color.White
                )
            ) {
                Text("Login", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // Siapkan NavController dummy untuk kebutuhan preview
    LoginScreen(
        rootNavController = rememberNavController(),
        authNavController = rememberNavController()
    )
}