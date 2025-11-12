package com.kelompok1.polnesnews.ui.user

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.kelompok1.polnesnews.components.VideoThumbnailCard
import com.kelompok1.polnesnews.model.Comment
import com.kelompok1.polnesnews.model.DummyData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    onNavigateBack: () -> Unit,
    newsId: Int
) {
    // Ambil semua data terkait berita.
    // 'remember' dipakai agar query ini hanya jalan lagi jika newsId berubah.
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
        // Handle jika ID berita tidak valid atau berita tidak ditemukan.
        if (news == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Berita tidak ditemukan.")
            }
            return@Scaffold // Stop render di sini
        }

        // Ambil context untuk dipakai di Intent video
        val context = LocalContext.current

        // Konten utama dibuat di LazyColumn agar bisa scroll
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // Judul Artikel
            item {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Gambar Utama
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

            // Info Author & Tanggal
            item {
                Spacer(modifier = Modifier.height(16.dp))
                AuthorDateRow(
                    authorName = author?.name ?: "Unknown",
                    date = news.date
                )
            }

            // Konten Artikel (render dari HTML)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HtmlText(
                    html = news.content,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Tampilkan video thumbnail HANYA jika ID-nya ada
            if (news.youtubeVideoId != null) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Video",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    )

                    // Card thumbnail video palsu
                    VideoThumbnailCard(
                        youtubeVideoId = news.youtubeVideoId,
                        onClick = {
                            // Buka link YouTube di browser/app
                            val webIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.youtube.com/watch?v=${news.youtubeVideoId}")
                            )
                            context.startActivity(webIntent)
                        },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            // Input Rating Bintang
            item {
                Spacer(modifier = Modifier.height(24.dp))
                ArticleRatingInput() // Helper di bawah
            }

            // Judul "User Ratings"
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "User Ratings",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Ringkasan rata-rata rating
            item {
                RatingSummary(comments = comments) // Helper di bawah
            }
        }
    }
}

// --- Helper Composables (khusus layar ini) ---

/**
 * Menampilkan info Author dan Tanggal berdampingan.
 */
@Composable
private fun AuthorDateRow(authorName: String, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Card Author
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
        // Card Tanggal
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
 * Input 5 bintang (1-5) dari user.
 */
@Composable
private fun ArticleRatingInput() {
    var userRating by remember { mutableStateOf(0) } // Simpan rating pilihan user
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
                        // Bintang isi (filled) vs bintang kosong (outline)
                        imageVector = if (index <= userRating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Rate $index",
                        tint = Color(0xFFFFC107), // Kuning emas
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}

/**
 * Menampilkan ringkasan rata-rata rating dari semua user.
 */
@Composable
private fun RatingSummary(comments: List<Comment>) {
    // Tampilkan pesan jika belum ada rating
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
                    // Logika untuk bintang penuh, setengah, atau kosong
                    val icon = when {
                        averageRating >= index -> Icons.Filled.Star
                        averageRating >= index - 0.5 -> Icons.Filled.StarHalf
                        else -> Icons.Outlined.StarOutline
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
 * Merender string HTML ke TextView.
 * Perlu AndroidView karena Text() Compose belum bisa parse tag <b>, <i>, dll.
 */
@Composable
private fun HtmlText(html: String, modifier: Modifier = Modifier) {
    // Ambil style dari tema
    val textStyle = MaterialTheme.typography.bodyLarge
    val textColor = MaterialTheme.colorScheme.onSurface

    // Ambil lineHeight dalam Pixel (px)
    val lineHeightInPx = with(LocalDensity.current) {
        textStyle.lineHeight.toPx().roundToInt()
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            // 'factory': Atur style TextView sekali saat dibuat
            TextView(context).apply {
                setTextColor(textColor.toArgb())
                textSize = textStyle.fontSize.value
                setLineSpacing((lineHeightInPx - lineHeight).toFloat(), 1.0f)
                linksClickable = true // Aktifkan link <a>
            }
        },
        update = {
            // 'update': Dipanggil saat input 'html' berubah
            it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    )
}

// --- Preview ---
@Preview(showBackground = true)
@Composable
private fun NewsDetailScreenPreview() {
    // Preview ini pakai berita ID 1 (yang punya video & komen)
    NewsDetailScreen(
        onNavigateBack = {},
        newsId = 1
    )
}