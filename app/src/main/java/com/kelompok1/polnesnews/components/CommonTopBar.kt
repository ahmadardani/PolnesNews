package com.kelompok1.polnesnews.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    title: String,
    onBack: (() -> Unit)? = null // 'onBack' ini opsional (nullable)
) {
    // Pakai CenterAlignedTopAppBar dari Material 3
    // Ini bikin judulnya otomatis di tengah tanpa perlu modifier macem-macem
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
                // Nggak perlu Modifier.fillMaxWidth() atau TextAlign.Center lagi
            )
        },
        navigationIcon = {
            // Tampilkan tombol 'back' HANYA jika fungsi onBack-nya diisi (tidak null)
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Kembali",
                        tint = Color.White // Pastikan ikonnya putih
                    )
                }
            }
        },
        // Atur skema warna TopAppBar-nya di sini
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF038900), // Warna hijau utama aplikasi
            titleContentColor = Color.White // Warna untuk judul
        )
    )
}