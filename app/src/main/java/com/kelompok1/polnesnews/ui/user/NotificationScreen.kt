package com.kelompok1.polnesnews.ui.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.components.NotificationCard
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.components.UserBottomNav
import com.kelompok1.polnesnews.model.DummyData

/**
 * Layar yang menampilkan daftar semua notifikasi.
 */
@Composable
fun NotificationsScreen() {
    // Ambil data notifikasi (dummy)
    val notifications = DummyData.notifications

    // Gunakan LazyColumn untuk menampilkan daftar notifikasi
    // agar efisien (hanya merender yang tampil di layar) dan bisa di-scroll.
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Loop untuk setiap item di 'notifications'
        items(notifications) { notification ->
            // Panggil Composable 'NotificationCard' yang sudah reusable
            NotificationCard(notification = notification)
        }
    }
}

// --- PREVIEW 1: HANYA KONTEN ---
@Preview(showBackground = true, name = "Hanya Konten (Default)")
@Composable
private fun NotificationsScreenPreview() {
    // PolnesNewsTheme {
    // Preview ini hanya merender konten layarnya saja,
    // berguna untuk mengecek UI komponennya.
    NotificationsScreen()
    // }
}

// --- PREVIEW 2: TAMPILAN PENUH ---
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Tampilan Penuh (Full App)")
@Composable
private fun FullNotificationsScreenPreview() {
    // PolnesNewsTheme {
    // Preview ini mensimulasikan bagaimana layar ini akan terlihat
    // di dalam aplikasi lengkap dengan TopBar dan BottomNav.
    Scaffold(
        topBar = {
            TitleOnlyTopAppBar(title = "Notifications")
        },
        bottomBar = {
            UserBottomNav(
                currentRoute = "Notifications", // Tandai "Notifications" sebagai aktif
                onItemClick = {} // Tidak perlu aksi di preview
            )
        }
    ) { innerPadding ->
        // Terapkan padding dari Scaffold ke konten
        Box(modifier = Modifier.padding(innerPadding)) {
            NotificationsScreen()
        }
    }
    // }
}