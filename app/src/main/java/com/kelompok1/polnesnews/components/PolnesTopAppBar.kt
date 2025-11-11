package com.kelompok1.polnesnews.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolnesTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_polnes_news),
                    contentDescription = "Logo Polnes News",
                    // Memberi warna putih pada logo XML (vector drawable)
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.height(30.dp) // Sesuaikan ukuran logo
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF038900), // Warna hijau
            titleContentColor = Color.White // Warna untuk logo (tint)
        ),
        modifier = modifier
    )
}