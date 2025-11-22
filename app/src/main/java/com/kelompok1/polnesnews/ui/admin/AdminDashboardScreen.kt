package com.kelompok1.polnesnews.ui.admin

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok1.polnesnews.components.AccountInfoCard
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.News
import com.kelompok1.polnesnews.model.NewsStatus
import com.kelompok1.polnesnews.model.User
import com.kelompok1.polnesnews.model.UserRole
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

@Composable
fun AdminDashboardScreen(
    currentUser: User?
) {
    // --- 1. Hitung Data Statistik Real-time ---
    val pendingNews = DummyData.newsList.count {
        it.status == NewsStatus.PENDING_REVIEW || it.status == NewsStatus.PENDING_DELETION
    }
    val publishedNews = DummyData.newsList.count { it.status == NewsStatus.PUBLISHED }
    val totalViews = DummyData.newsList.sumOf { it.views }
    val totalEditors = DummyData.userList.count { it.role == UserRole.EDITOR }
    val totalReaders = DummyData.userList.count { it.role == UserRole.USER }
    val totalCategories = DummyData.categoryList.size

    // --- 2. Hitung Data untuk Chart & Top Lists ---

    // A. Data Pie Chart (Total Views per Kategori)
    val categoryViewsMap = remember {
        DummyData.categoryList.map { category ->
            val viewsInCategory = DummyData.newsList
                .filter { it.categoryId == category.id }
                .sumOf { it.views }
            category.name to viewsInCategory
        }.filter { it.second > 0 } // Hanya ambil kategori yang ada views-nya
    }

    // B. Top 3 Berita Terpopuler (Berdasarkan Views)
    val topNews = remember {
        DummyData.newsList
            .sortedByDescending { it.views }
            .take(3)
    }

    // C. Top 3 Editor Terbaik (Berdasarkan Rating Rata-rata)
    // Kita buat struktur data sementara untuk menampung Editor + Ratingnya
    data class EditorPerformance(val user: User, val avgRating: Double)

    val topEditors = remember {
        DummyData.userList
            .filter { it.role == UserRole.EDITOR }
            .map { editor ->
                // Hitung rating (Logic dari EditorDashboard)
                val newsByEditor = DummyData.newsList.filter { it.authorId == editor.id }
                val ratings = DummyData.commentList.filter { comment ->
                    newsByEditor.any { it.id == comment.newsId }
                }.map { it.rating }

                val avg = if (ratings.isNotEmpty()) ratings.average() else 0.0
                EditorPerformance(editor, avg)
            }
            .sortedByDescending { it.avgRating }
            .take(3)
    }

    // === COLUMN UTAMA ===
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // --- Header Akun ---
        AccountInfoCard(
            fullName = currentUser?.name ?: "Admin",
            role = "Administrator",
            modifier = Modifier.fillMaxWidth()
        )

        // --- Konten Dashboard (Padding Wrapper) ---
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // ==============================
            // BAGIAN 1: SYSTEM OVERVIEW
            // ==============================
            Text(
                text = "System Overview",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Row 1: Status
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                AdminStatCard(Modifier.weight(1f), "Need Review", pendingNews.toString(), MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.onErrorContainer)
                AdminStatCard(Modifier.weight(1f), "Published", publishedNews.toString(), MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.onPrimaryContainer)
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Row 2: Users
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                AdminStatCard(Modifier.weight(1f), "Total Editors", totalEditors.toString(), MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.colorScheme.onTertiaryContainer)
                AdminStatCard(Modifier.weight(1f), "Total Readers", totalReaders.toString(), MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Row 3: Engagement
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                AdminStatCard(Modifier.weight(1f), "Total Views", totalViews.toString(), MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.onSecondaryContainer)
                AdminStatCard(Modifier.weight(1f), "Categories", totalCategories.toString(), MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.height(32.dp))
            Divider(color = Color.LightGray.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.height(24.dp))

            // ==============================
            // BAGIAN 2: CATEGORY ANALYTICS (PIE CHART)
            // ==============================
            Text(
                text = "Category Engagement", // Nama yang lebih profesional
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (categoryViewsMap.isEmpty()) {
                Text("No data available for chart", color = Color.Gray)
            } else {
                // Tampilkan Chart Component
                SimplePieChart(data = categoryViewsMap)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ==============================
            // BAGIAN 3: TOP TRENDING NEWS
            // ==============================
            SectionHeader(title = "Top Trending News", icon = Icons.Outlined.Visibility)

            if (topNews.isEmpty()) {
                Text("No news available.", color = Color.Gray)
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    topNews.forEachIndexed { index, news ->
                        TopNewsItemCard(rank = index + 1, news = news)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ==============================
            // BAGIAN 4: TOP PERFORMING EDITORS
            // ==============================
            SectionHeader(title = "Top Performing Editors", icon = Icons.Outlined.Person)

            if (topEditors.isEmpty()) {
                Text("No editors data available.", color = Color.Gray)
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    topEditors.forEachIndexed { index, data ->
                        TopEditorItemCard(rank = index + 1, user = data.user, rating = data.avgRating)
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// ------------------------------------------------
// HELPER COMPONENTS (UI WIDGETS)
// ------------------------------------------------

@Composable
fun SectionHeader(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

// --- PIE CHART COMPONENT ---
@Composable
fun SimplePieChart(data: List<Pair<String, Int>>) {
    val total = data.sumOf { it.second }.toFloat()
    // Warna statis untuk chart (bisa ditambah jika kategori banyak)
    val chartColors = listOf(
        Color(0xFF6200EE), // Ungu
        Color(0xFF03DAC5), // Teal
        Color(0xFFFF9800), // Orange
        Color(0xFF2196F3), // Biru
        Color(0xFFE91E63)  // Pink
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // 1. The Pie Chart (Canvas)
        Box(
            modifier = Modifier.size(140.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(120.dp)) {
                var startAngle = -90f
                data.forEachIndexed { index, pair ->
                    val sweepAngle = (pair.second / total) * 360f
                    val color = chartColors.getOrElse(index) { Color.Gray }

                    drawArc(
                        color = color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = Stroke(width = 40f) // Donat Style
                    )
                    startAngle += sweepAngle
                }
            }
            // Text Total di tengah Donat
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = total.toInt().toString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = "Views", fontSize = 10.sp, color = Color.Gray)
            }
        }

        // 2. The Legend (Keterangan Warna)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            data.forEachIndexed { index, pair ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(chartColors.getOrElse(index) { Color.Gray })
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = pair.first, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        Text(text = "${pair.second} views", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                }
            }
        }
    }
}

// --- TOP NEWS ITEM CARD ---
@Composable
fun TopNewsItemCard(rank: Int, news: News) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank Number
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        if (rank == 1) Color(0xFFFFD700) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "#$rank", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Category ID: ${news.categoryId}", // Bisa diganti Nama Category kalau mau lookup
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            // View Count Badge
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.Visibility, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = news.views.toString(), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// --- TOP EDITOR ITEM CARD ---
@Composable
fun TopEditorItemCard(rank: Int, user: User, rating: Double) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar Sederhana
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.name.take(1).uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Rating Badge
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107), // Warna Emas
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format("%.1f", rating),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(text = "Avg Rating", fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}

// --- Helper Component: AdminStatCard (Tetap Sama) ---
@Composable
fun AdminStatCard(
    modifier: Modifier = Modifier,
    label: String,
    count: String,
    containerColor: Color,
    contentColor: Color
) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            Text(
                text = label,
                fontSize = 14.sp,
                color = contentColor.copy(alpha = 0.8f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AdminDashboardPreview() {
    PolnesNewsTheme {
        val mockAdmin = DummyData.userList.find { it.role == UserRole.ADMIN }
        AdminDashboardScreen(currentUser = mockAdmin)
    }
}