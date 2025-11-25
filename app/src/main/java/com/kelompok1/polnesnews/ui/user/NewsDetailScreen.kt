package com.kelompok1.polnesnews.ui.user

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage // 🟢 WAJIB: Import Coil
import com.kelompok1.polnesnews.components.*
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.utils.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    onNavigateBack: () -> Unit,
    newsId: Int
) {
    val news = remember(newsId) { DummyData.newsList.find { it.id == newsId } }
    val author = remember(news?.authorId) { DummyData.userList.find { it.id == news?.authorId } }
    val comments = remember(newsId) { DummyData.commentList.filter { it.newsId == newsId } }

    val currentUser = SessionManager.currentUser
    var userRating by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    fun submitRatingToDatabase() {
        if (currentUser == null) {
            Toast.makeText(context, "Silakan login terlebih dahulu!", Toast.LENGTH_SHORT).show()
            return
        }

        // Simulasi Kirim
        Toast.makeText(
            context,
            "Rating $userRating bintang dari ${currentUser.name} terkirim!",
            Toast.LENGTH_SHORT
        ).show()

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

            // 🟢 REVISI GAMBAR UTAMA: Menggunakan AsyncImage (URL)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    model = news.imageUrl, // URL String
                    contentDescription = news.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )
            }

            // AUTHOR & DATE
            item {
                Spacer(modifier = Modifier.height(16.dp))
                AuthorDateRow(
                    authorName = author?.name ?: "Unknown",
                    // 🟢 REVISI: Format Tanggal agar cantik
                    date = DummyData.formatDate(news.date)
                )
            }

            // KONTEN
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HtmlText(html = news.content, modifier = Modifier.padding(horizontal = 16.dp))
            }

            // VIDEO
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