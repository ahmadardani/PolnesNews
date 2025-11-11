package com.kelompok1.polnesnews.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Composable untuk Bottom Navigation Bar utama.
 * Komponen ini 'stateless' (tidak mengelola state-nya sendiri).
 * Item yang aktif dikontrol dari luar melalui parameter [currentRoute].
 */
@Composable
fun UserBottomNav(
    modifier: Modifier = Modifier,
    currentRoute: String, // Rute yang sedang aktif (misal: "Home"), untuk highlight item
    onItemClick: (String) -> Unit
) {
    // State 'selectedItemIndex' sudah tidak ada di sini (state hoisting).
    // Item yang 'selected' ditentukan oleh 'currentRoute' dari pemanggil.
    val items = listOf("Home", "Categories", "Notifications", "Settings")

    NavigationBar(modifier = modifier) {
        items.forEach { label -> // (index tidak diperlukan lagi)

            // Logika 'selected': Item ini aktif jika 'label'-nya sama dengan 'currentRoute'
            val isSelected = (currentRoute == label)

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onItemClick(label) // Saat diklik, kirim 'label' (rute) ke pemanggil
                },
                label = { Text(text = label) },
                icon = {
                    Icon(
                        imageVector = getIconForLabel(label),
                        contentDescription = label
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    // Atur warna kustom
                    selectedIconColor = Color(0xFF375E2F),
                    selectedTextColor = Color(0xFF375E2F),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent // Sembunyikan background 'pil' M3
                )
            )
        }
    }
}

/**
 * Fungsi helper privat untuk memetakan String label
 * ke ImageVector ikon yang sesuai.
 */
@Composable
private fun getIconForLabel(label: String): ImageVector {
    return when (label) {
        "Home" -> Icons.Filled.Home
        "Categories" -> Icons.Filled.Category
        "Notifications" -> Icons.Filled.Notifications
        "Settings" -> Icons.Filled.Settings
        else -> Icons.Filled.Home // Default ke Home jika ada label yang tidak dikenal
    }
}