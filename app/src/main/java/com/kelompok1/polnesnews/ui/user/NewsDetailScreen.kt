package com.kelompok1.polnesnews.ui.user

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.platform.LocalDensity
import kotlin.math.roundToInt
import androidx.core.text.HtmlCompat
import com.kelompok1.polnesnews.components.CommonTopBar
import com.kelompok1.polnesnews.model.Comment
import com.kelompok1.polnesnews.model.DummyData
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    onNavigateBack: () -> Unit,
    newsId: Int
) {
    // 1. Ambil semua data yang diperlukan berdasarkan newsId
    // 'remember(key)' dipakai agar data ini di-fetch ulang HANYA jika 'newsId'-nya berubah.
    val news = remember(newsId) {
        DummyData.newsList.find { it.id == newsId }
    }
    val author = remember(news?.authorId) {
        DummyData.userList.find { it.id == news?.authorId }
    }
    val comments = remember(newsId) {
        DummyData.commentList.filter { it.newsId == newsId }
    }

    Scaffold(
        topBar = {
            CommonTopBar(
                title = "Article",
                onBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        // Handle kasus jika berita dengan ID tersebut tidak ditemukan (misal, ID salah)
        if (news == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Berita tidak ditemukan.")
            }
            return@Scaffold // Hentikan eksekusi Composable di sini
        }

        // 2. Tampilkan konten berita di dalam LazyColumn agar bisa di-scroll
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // Bagian Judul
            item {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Bagian Gambar Utama Berita
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = news.imageRes),
                    contentDescription = news.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f) // Paksa rasio 16:9
                )
            }

            // Bagian Info Author dan Tanggal (custom component)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                AuthorDateRow(
                    authorName = author?.name ?: "Unknown",
                    date = news.date
                )
            }

            // Bagian Konten/Isi Artikel (dirender dari HTML)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HtmlText( // Pakai helper HtmlText di bawah
                    html = news.content,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Tampilkan pemutar YouTube HANYA jika ID videonya ada
            if (news.youtubeVideoId != null) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    // Panggil helper composable YouTubePlayer di bawah
                    YouTubePlayer(videoId = news.youtubeVideoId)
                }
            }

            // Bagian Input Rating (bintang 1-5)
            item {
                Spacer(modifier = Modifier.height(24.dp))
                ArticleRatingInput() // Helper di bawah
            }

            // Judul sub-seksi "User Ratings"
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "User Ratings",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Bagian Ringkasan Rating (rata-rata & total)
            item {
                RatingSummary(comments = comments) // Helper di bawah
            }
        }
    }
}

// --- Helper Composables (khusus untuk layar ini) ---

/**
 * Menampilkan dua Card berdampingan untuk Author dan Tanggal.
 */
@Composable
private fun AuthorDateRow(authorName: String, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Card Kiri: Author
        Card(
            modifier = Modifier.weight(1f),
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Author", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
                Text(text = authorName, style = MaterialTheme.typography.bodyMedium)
            }
        }
        // Card Kanan: Tanggal
        Card(
            modifier = Modifier.weight(1f),
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Date", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
                Text(text = date, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
            }
        }
    }
}

/**
 * Composable wrapper untuk 'AndroidView' yang menampung library YouTubePlayerView.
 * Ini adalah cara untuk memakai View Android (non-Compose) di dalam Compose.
 */
@Composable
private fun YouTubePlayer(videoId: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Video",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // AndroidView digunakan untuk 'embed' View Android tradisional
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(MaterialTheme.shapes.medium),
            factory = { context ->
                // 'factory' hanya dijalankan sekali untuk MEMBUAT View-nya
                YouTubePlayerView(context).apply {
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            // 'cueVideo' akan me-load video tapi tidak auto-play, lebih stabil.
                            youTubePlayer.cueVideo(videoId, 0f)
                        }
                    })
                }
            }
        )
    }
}

/**
 * Composable untuk input 5 bintang (1-5) dari user
 */
@Composable
private fun ArticleRatingInput() {
    var userRating by remember { mutableStateOf(0) } // State untuk menyimpan rating user
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rate this article",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            (1..5).forEach { index ->
                IconButton(onClick = { userRating = index }) {
                    Icon(
                        // Tampilkan ikon Bintang Isi (Filled) jika 'index' <= rating, jika tidak, Bintang Kosong (Outline)
                        imageVector = if (index <= userRating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Rate $index",
                        tint = Color(0xFFFFC107), // Warna kuning emas
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}

/**
 * Menampilkan ringkasan rata-rata rating dari semua user
 */
@Composable
private fun RatingSummary(comments: List<Comment>) {
    // Tampilkan pesan jika belum ada yang memberi rating
    if (comments.isEmpty()) {
        Text(
            text = "No ratings yet for this article.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        return
    }

    val totalRatings = comments.size
    val averageRating = comments.sumOf { it.rating }.toFloat() / totalRatings

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Kiri: Angka rata-rata
            Column {
                Text(
                    text = "%.1f".format(averageRating), // Format jadi 1 angka desimal
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Based on $totalRatings ratings",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            // Kanan: Visualisasi bintang
            Row {
                (1..5).forEach { index ->
                    // Logika untuk menampilkan bintang penuh, setengah, atau kosong
                    val icon = when {
                        averageRating >= index -> Icons.Filled.Star // Penuh
                        averageRating >= index - 0.5 -> Icons.Filled.StarHalf // Setengah
                        else -> Icons.Outlined.StarOutline // Kosong
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

/**
 * Composable 'interop' untuk merender string HTML ke dalam TextView Android.
 * Dibutuhkan karena Composable Text() bawaan (saat ini) tidak bisa parse tag HTML (spt <b>).
 */
@Composable
private fun HtmlText(html: String, modifier: Modifier = Modifier) {
    // Ambil style dari MaterialTheme
    val textStyle = MaterialTheme.typography.bodyLarge
    val textColor = MaterialTheme.colorScheme.onSurface

    // Ambil lineHeight DALAM PIXEL (px)
    val lineHeightInPx = with(LocalDensity.current) {
        textStyle.lineHeight.toPx().roundToInt()
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            // 'factory': Atur style TextView (warna, ukuran) saat pertama kali dibuat.
            TextView(context).apply {
                setTextColor(textColor.toArgb())
                textSize = textStyle.fontSize.value

                // Set lineHeight dalam PIXEL (Cara yang benar untuk TextView)
                setLineSpacing((lineHeightInPx - lineHeight).toFloat(), 1.0f)

                linksClickable = true // Jika ada <a> tag
            }
        },
        update = {
            // 'update': Dipanggil saat 'html' (input) berubah.
            // Gunakan parser HtmlCompat untuk mengubah string HTML jadi Spanned text.
            it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    )
}

// --- Preview ---
@Preview(showBackground = true)
@Composable
private fun NewsDetailScreenPreview() {
    // Preview ini akan merender berita dengan ID 1,
    // yang (berdasarkan DummyData) punya video dan komentar.
    NewsDetailScreen(
        onNavigateBack = {},
        newsId = 1
    )
}