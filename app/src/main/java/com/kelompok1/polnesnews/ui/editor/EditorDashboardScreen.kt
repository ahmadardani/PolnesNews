package com.kelompok1.polnesnews.ui.editor

// TAMBAHKAN IMPORT INI
// IMPORT BARU UNTUK KOMPONEN
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.NewsStatus
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import com.kelompok1.polnesnews.components.EditorBottomNav
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorDashboardScreen(
    editorId: Int,
    currentRoute: String, // <-- PERUBAHAN 1: Ditambahkan
    onNavigate: (String) -> Unit // <-- PERUBAHAN 1: Ditambahkan
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
        topBar = {
            // PERUBAHAN 2: Menggunakan TitleOnlyTopAppBar
            TitleOnlyTopAppBar(title = "Dashboard")
        },
        // PERUBAHAN 3: Menambahkan bottomBar (Navbar)
        bottomBar = {
            EditorBottomNav(
                currentRoute = currentRoute,
                onNavigate = onNavigate
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding) // Padding dari Scaffold
                .padding(16.dp), // Padding konten di dalam LazyColumn
            verticalArrangement = Arrangement.spacedBy(16.dp) // Jarak antar item
        ) {
            item {
                Text(
                    text = "Your Performance Overview",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 0.dp)
                )
            }

            // Grup Row 1
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DashboardCard("Avg Rating", String.format("%.1f", averageRating))
                    DashboardCard("Total Views", totalViews.toString())
                }
            }

            // Grup Row 2
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DashboardCard("Articles", totalArticles.toString())
                    DashboardCard("Approved", approvedCount.toString())
                }
            }

            // Grup Row 3
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DashboardCard("Pending", pendingCount.toString())
                }
            }

            // Performance Text
            item {
                Text(
                    text = "Performance Status", // <-- Teks judul baru
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp) // Jarak dari card di atas
                )
            }

            // 2. Box Card berisi nilai
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    // Beri warna sedikit berbeda agar menonjol
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    // Gunakan Box agar teks bisa center
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp), // Padding lebih besar
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = performanceText,
                            fontSize = 20.sp, // Font lebih besar
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

@Preview(showBackground = true, name = "Editor Dashboard Screen")
@Composable
private fun EditorDashboardScreenPreview() {
    // Panggil tema Anda sebagai fungsi, yang akan membungkus konten preview
    PolnesNewsTheme { // <-- PERBAIKAN: Dipanggil sebagai fungsi
        EditorDashboardScreen(
            editorId = 1,
            currentRoute = "editor_dashboard", // Tandai 'Dashboard' sebagai aktif
            onNavigate = {} // Sediakan lambda kosong untuk preview
        )
    }
}