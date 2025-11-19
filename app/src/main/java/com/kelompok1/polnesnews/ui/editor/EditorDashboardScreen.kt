package com.kelompok1.polnesnews.ui.editor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.NewsStatus
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorDashboardScreen(
    editorId: Int,
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val newsByEditor = DummyData.newsList.filter { it.authorId == editorId }
    val ratings = DummyData.commentList.filter { comment ->
        newsByEditor.any { it.id == comment.newsId }
    }.map { it.rating }

    val averageRating = if (ratings.isNotEmpty()) ratings.average() else 0.0
    val totalViews = newsByEditor.sumOf { it.views }
    val totalArticles = newsByEditor.size
    val approvedCount = newsByEditor.count { it.status == NewsStatus.PUBLISHED }
    val pendingCount = newsByEditor.count { it.status == NewsStatus.PENDING_REVIEW }

    val performanceText = when {
        averageRating >= 4.5 -> "Excellent work!"
        averageRating >= 3.5 -> "Great effort!"
        averageRating >= 2.5 -> "Fair, keep improving!"
        else -> "Needs improvement!"
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        // ✅ PERBAIKAN: Top Bar dipasang di sini
        topBar = {
            TitleOnlyTopAppBar(title = "Dashboard")
        }
        // 🔴 PERBAIKAN: bottomBar DIHAPUS dari sini
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Your Performance Overview",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 0.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DashboardCard("Avg Rating", String.format("%.1f", averageRating))
                    DashboardCard("Total Views", totalViews.toString())
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DashboardCard("Articles", totalArticles.toString())
                    DashboardCard("Approved", approvedCount.toString())
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DashboardCard("Pending", pendingCount.toString())
                }
            }

            item {
                Text(
                    text = "Performance Status",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = performanceText,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditorDashboardScreenPreview() {
    PolnesNewsTheme {
        EditorDashboardScreen(
            editorId = 1,
            currentRoute = "editor_dashboard",
            onNavigate = {}
        )
    }
}