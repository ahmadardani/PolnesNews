package com.kelompok1.polnesnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.kelompok1.polnesnews.navigation.AuthNavGraph
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolnesNewsTheme {
                val navController = rememberNavController()
                AuthNavGraph(navController)
            }
        }
    }
}
