package com.kelompok1.polnesnews.ui.home // Sesuaikan package Anda

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.R
import com.kelompok1.polnesnews.components.NewsCard
import com.kelompok1.polnesnews.model.DummyData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    // Tambahkan NavController jika sudah siap untuk navigasi
    // navController: NavController
) {
    // 1. Memproses data untuk menemukan satu berita teratas
    val latestNews = DummyData.newsList.sortedByDescending { it.date }.firstOrNull()
    val topViewedNews = DummyData.newsList.sortedByDescending { it.views }.firstOrNull()

    Scaffold(
        topBar = {
            PolnesTopAppBar()
        },
        bottomBar = {
            PolnesBottomNav(
                onItemClick = { /* TODO: Handle Navigasi */ }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // --- Bagian Recent News ---
            item {
                SectionHeader(
                    title = "Recent News",
                    subtitle = "Here is the latest news from Polnes News",
                    onViewAllClick = { /* TODO: Navigasi ke halaman 'All Recent News' */ }
                )
            }
            item {
                // Tampilkan hanya satu kartu berita terbaru
                if (latestNews != null) {
                    NewsCard(
                        news = latestNews,
                        onClick = { /* TODO: Navigasi ke detail berita (latestNews.id) */ }
                    )
                } else {
                    // Tampilan jika tidak ada berita
                    Text(
                        text = "No recent news available.",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            // --- Bagian Most Viewed News ---
            item {
                SectionHeader(
                    title = "Most Viewed News",
                    subtitle = "Most frequently viewed news on Polnes News",
                    onViewAllClick = { /* TODO: Navigasi ke halaman 'All Most Viewed' */ }
                )
            }
            item {
                // Tampilkan hanya satu kartu berita terpopuler
                if (topViewedNews != null) {
                    NewsCard(
                        news = topViewedNews,
                        onClick = { /* TODO: Navigasi ke detail berita (topViewedNews.id) */ }
                    )
                } else {
                    // Tampilan jika tidak ada berita
                    Text(
                        text = "No most viewed news available.",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            // Spacer di akhir list agar tidak terpotong BottomNav
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// --- Komponen Top App Bar ---
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

// --- Komponen Header per Bagian ---
@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    onViewAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 24.dp, bottom = 8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        TextButton(onClick = onViewAllClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF4AB148))) {
            Text(text = "View More")
        }
    }
}

// --- Komponen Bottom Navigation Bar ---
@Composable
fun PolnesBottomNav(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit
) {
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val items = listOf("Home", "Categories", "Notifications", "Settings")

    NavigationBar(modifier = modifier) {
        items.forEachIndexed { index, label ->
            val isSelected = selectedItemIndex == index
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selectedItemIndex = index
                    onItemClick(label)
                },
                label = { Text(text = label) },
                icon = {
                    Icon(
                        imageVector = getIconForLabel(label),
                        contentDescription = label
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF375E2F), // Warna ikon aktif
                    selectedTextColor = Color(0xFF375E2F), // Warna teks aktif (disamakan)
                    unselectedIconColor = Color.Gray, // Warna saat tidak aktif
                    unselectedTextColor = Color.Gray, // Warna saat tidak aktif
                    indicatorColor = Color.Transparent // Menghilangkan latar belakang highlight
                )
            )
        }
    }
}

// Helper untuk mengambil ikon (Filled jika aktif, Outlined jika tidak)
@Composable
private fun getIconForLabel(label: String): ImageVector {
    return when (label) {
        "Home" -> Icons.Filled.Home
        "Categories" -> Icons.Filled.Category
        "Notifications" -> Icons.Filled.Notifications
        "Settings" -> Icons.Filled.Settings
        else -> Icons.Filled.Home
    }
}


// --- Preview (Opsional, untuk melihat di Android Studio) ---
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    // Anda mungkin perlu membungkus ini dengan Theme aplikasi Anda
    // Misal: PolnesNewsTheme { ... }
    HomeScreen()
}