package com.kelompok1.polnesnews.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            // Tampilkan tombol 'back' HANYA jika fungsi onBack-nya diisi (tidak null)
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Kembali",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        // Atur skema warna TopAppBar-nya di sini
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(

            containerColor = MaterialTheme.colorScheme.primary,  // Warna hijau utama aplikasi

            titleContentColor = MaterialTheme.colorScheme.onPrimary,  // Warna untuk judul

            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}