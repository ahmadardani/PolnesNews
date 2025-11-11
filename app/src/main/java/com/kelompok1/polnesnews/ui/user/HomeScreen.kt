package com.kelompok1.polnesnews.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.components.NewsCard
import com.kelompok1.polnesnews.components.PolnesTopAppBar
import com.kelompok1.polnesnews.components.SectionHeader
import com.kelompok1.polnesnews.components.UserBottomNav
import com.kelompok1.polnesnews.model.DummyData

/**
 * Layar utama (Home) aplikasi.
 * Layar ini 'stateless' terkait navigasi. Ia tidak tahu cara navigasi,
 * tapi ia memberi tahu pemanggilnya (UserNavGraph) saat ada yang diklik
 * melalui parameter lambda [onViewAllRecent], [onViewAllMostViewed], dan [onNewsClick].
 */
@Composable
fun HomeScreen(
    // Lambda ini akan dipanggil saat tombol "View All" di "Recent News" diklik
    onViewAllRecent: () -> Unit,
    // Lambda ini akan dipanggil saat tombol "View All" di "Most Viewed" diklik
    onViewAllMostViewed: () -> Unit,
    // Lambda ini akan dipanggil saat sebuah NewsCard diklik,
    // sambil membawa ID berita yang diklik.
    onNewsClick: (Int) -> Unit
) {
    // Ambil data dummy (sementara)
    val latestNews = DummyData.newsList.sortedByDescending { it.date }.firstOrNull()
    val topViewedNews = DummyData.newsList.sortedByDescending { it.views }.firstOrNull()

    // Pakai LazyColumn karena konten layar ini perlu di-scroll secara vertikal
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // --- Bagian Recent News ---
        item {
            SectionHeader(
                title = "Recent News",
                subtitle = "Here is the latest news from Polnes News",
                onViewAllClick = onViewAllRecent // Teruskan lambda-nya ke SectionHeader
            )
        }
        item {
            if (latestNews != null) {
                NewsCard(
                    news = latestNews,
                    // Saat card ini diklik, panggil lambda 'onNewsClick'
                    // dengan membawa ID dari 'latestNews'
                    onClick = { onNewsClick(latestNews.id) }
                )
            } else {
                // Tampilkan pesan jika tidak ada berita
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
                onViewAllClick = onViewAllMostViewed // Teruskan lambda-nya
            )
        }
        item {
            if (topViewedNews != null) {
                NewsCard(
                    news = topViewedNews,
                    // Saat card ini diklik, panggil lambda 'onNewsClick'
                    // dengan membawa ID dari 'topViewedNews'
                    onClick = { onNewsClick(topViewedNews.id) }
                )
            } else {
                Text(
                    text = "No most viewed news available.",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        // Spacer di bagian paling bawah list agar tidak terlalu mepet
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


// --- Preview 1: Hanya Konten ---
@Preview(showBackground = true, name = "Hanya Konten (Default)")
@Composable
fun HomeScreenPreview() {
    // Kita harus menyediakan lambda kosong ( {}) agar
    // preview bisa me-render composable ini.
    HomeScreen(
        onViewAllRecent = {},
        onViewAllMostViewed = {},
        onNewsClick = {}
    )
}

// --- Preview 2: Tampilan Penuh ---
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Tampilan Penuh (Full App)")
@Composable
fun FullHomeScreenPreview() {
    // PolnesNewsTheme {
    Scaffold(
        topBar = {
            PolnesTopAppBar()
        },
        bottomBar = {
            UserBottomNav(
                currentRoute = "Home",
                onItemClick = {}
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // Sama seperti preview sebelumnya, kita sediakan lambda kosong
            // hanya untuk kebutuhan rendering preview.
            HomeScreen(
                onViewAllRecent = {},
                onViewAllMostViewed = {},
                onNewsClick = {}
            )
        }
    }
    // }
}