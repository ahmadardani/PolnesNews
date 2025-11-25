package com.kelompok1.polnesnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kelompok1.polnesnews.navigation.AppNavigation
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengaktifkan tampilan layar penuh (status bar transparan)
        enableEdgeToEdge()

        setContent {
            PolnesNewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // CUKUP PANGGIL INI.
                    // Semua logika NavHost, Login, Admin, User, Editor
                    // sudah diatur rapi di dalam file AppNavigation.kt
                    AppNavigation()
                }
            }
        }
    }
}