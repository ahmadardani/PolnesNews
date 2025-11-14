package com.kelompok1.polnesnews.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.RowScope // <-- 1. IMPORT RowScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    title: String,
    onBack: (() -> Unit)? = null, // 'onBack' ini opsional (nullable)
    // --- 2. TAMBAHKAN PARAMETER ACTIONS ---
    // Default-nya adalah kosong, jadi tidak akan merusak screen lain
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
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
        // --- 3. MASUKKAN ACTIONS KE TOP APP BAR ---
        actions = actions, // <-- Tambahkan baris ini

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}