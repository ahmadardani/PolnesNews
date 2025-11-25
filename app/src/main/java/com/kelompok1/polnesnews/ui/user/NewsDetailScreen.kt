package com.kelompok1.polnesnews.ui.user

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.components.*
import com.kelompok1.polnesnews.model.DummyData

// 🟢 SessionManager sudah DIHAPUS dari import

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    onNavigateBack: () -> Unit,
    newsId: Int
) {
    val context = LocalContext.current

    // Data Berita (Masih pakai DummyData untuk konten agar tidak kosong)
    val news = remember(newsId) { DummyData.newsList.find { it.id == newsId } }
    val author = remember(news?.authorId) { DummyData.userList.find { it.id == news?.authorId } }
    val comments = remember(newsId) { DummyData.commentList.filter { it.newsId == newsId } }

    var userRating by remember { mutableIntStateOf(0) }

    // 🟢 Logic Kirim Rating (Dibuat Polos / Placeholder)
    // Nanti teman backendmu tinggal isi logika API di sini
    fun submitRatingToDatabase() {
        if (userRating == 0) {
            Toast.makeText(context, "Silakan pilih bintang terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO: Backend Developer akan mengisi logika kirim ke API di sini

        // Simulasi UI saja
        Toast.makeText(
            context,
            "Rating $userRating bintang terkirim! (Menunggu Integrasi Backend)",
            Toast.LENGTH_SHORT
        ).show()

        // Reset rating
        userRating = 0
    }

    Scaffold(
        topBar = {
            CommonTopBar(
                title = "Article",
                onBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        if (news == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Berita tidak ditemukan.")
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // JUDUL
            item {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            // GAMBAR
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = news.imageRes),
                    contentDescription = news.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f)
                )
            }
            // AUTHOR & TANGGAL
            item {
                Spacer(modifier = Modifier.height(16.dp))
                AuthorDateRow(authorName = author?.name ?: "Unknown", date = news.date)
            }
            // KONTEN BERITA
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HtmlText(html = news.content, modifier = Modifier.padding(horizontal = 16.dp))
            }
            // VIDEO YOUTUBE
            if (news.youtubeVideoId != null) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Video", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 16.dp))
                    VideoThumbnailCard(
                        youtubeVideoId = news.youtubeVideoId,
                        onClick = {
                            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=${news.youtubeVideoId}"))
                            context.startActivity(webIntent)
                        },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            // INPUT RATING
            item {
                Spacer(modifier = Modifier.height(24.dp))

                // Text sapaan user dihapus karena kita tidak tahu siapa yang login

                ArticleRatingInput(
                    currentRating = userRating,
                    onRatingSelected = { userRating = it },
                    onSubmit = { submitRatingToDatabase() }
                )
            }

            // LIST KOMENTAR
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Rating Pengguna Lain",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                RatingSummary(comments = comments)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsDetailScreenPreview() {
    NewsDetailScreen(onNavigateBack = {}, newsId = 1)
}