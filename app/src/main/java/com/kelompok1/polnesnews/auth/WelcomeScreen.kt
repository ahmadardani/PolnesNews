package com.kelompok1.polnesnews.auth

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.kelompok1.polnesnews.R

@Composable
fun WelcomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Bagian 1: Tulisan Welcome ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Welcome!",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    ),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF038900)
                )
            }

            // --- Bagian 2: Gambar PNG di tengah ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .paint( // ⬇️ TAMBAHKAN MODIFIER INI
                        painter = painterResource(id = R.drawable.ic_doodle_background), // <-- Ganti ini
                        contentScale = ContentScale.Crop, // <-- Sesuaikan ini
                        alpha = 0.3f
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_polnes_news),
                    contentDescription = "Welcome Image",
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(220.dp)
                )
            }

            // --- Bagian 3: Spacer kecil ---
            Spacer(modifier = Modifier.height(16.dp))

            // --- Bagian 4: Tombol di bawah ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate("login") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF038900),
                        contentColor = Color.White
                    )
                ) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { navController.navigate("signup") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF038900),
                    )
                ) {
                    Text("Sign Up")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    val dummyNavController = rememberNavController()
    WelcomeScreen(navController = dummyNavController)
}
