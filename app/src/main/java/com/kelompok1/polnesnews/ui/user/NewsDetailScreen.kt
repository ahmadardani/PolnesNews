package com.kelompok1.polnesnews.ui.user

import android.content.Intent
import android.net.Uri
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    onNavigateBack: () -> Unit,
    newsId: Int
) {
    val news = remember(newsId) { DummyData.newsList.find { it.id == newsId } }
    val author = remember(news?.authorId) { DummyData.userList.find { it.id == news?.authorId } }
    val comments = remember(newsId) { DummyData.commentList.filter { it.newsId == newsId } }

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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Berita tidak ditemukan.")
            }
            return@Scaffold
        }

        val context = LocalContext.current

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
                        .aspectRatio(16f / 9f)
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

            // Konten Artikel (HTML)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HtmlText(
                    html = news.content,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Video Thumbnail (jika ada)
            if (news.youtubeVideoId != null) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Video",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    )

                    VideoThumbnailCard(
                        youtubeVideoId = news.youtubeVideoId,
                        onClick = {
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
                ArticleRatingInput()
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
                RatingSummary(comments = comments)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsDetailScreenPreview() {
    NewsDetailScreen(
        onNavigateBack = {},
        newsId = 1
    )
}
