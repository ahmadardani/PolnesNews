package com.kelompok1.polnesnews.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.components.StatusChip
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.News
import com.kelompok1.polnesnews.model.NewsStatus
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

@Composable
fun ManageNewsScreen() {
    // State Tab (0 = Needs Review, 1 = All News)
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Needs Review", "All News")

    // Filter Data
    val newsList = if (selectedTabIndex == 0) {
        DummyData.newsList.filter {
            it.status == NewsStatus.PENDING_REVIEW ||
                    it.status == NewsStatus.PENDING_DELETION ||
                    it.status == NewsStatus.PENDING_UPDATE
        }
    } else {
        DummyData.newsList
    }

    // 🔴 HAPUS SCAFFOLD & TOPBAR, LANGSUNG COLUMN
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Tab Row
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if(selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        // List Content
        if (newsList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = if (selectedTabIndex == 0) "No pending approvals." else "No news found.",
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(newsList) { article ->
                    AdminNewsCard(article = article)
                }
            }
        }
    }
}

@Composable
fun AdminNewsCard(article: News) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = article.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Status: ", style = MaterialTheme.typography.bodyMedium)
                StatusChip(status = article.status.name)
            }

            // Tombol Aksi (Hanya muncul jika PENDING)
            if (article.status == NewsStatus.PENDING_REVIEW ||
                article.status == NewsStatus.PENDING_DELETION ||
                article.status == NewsStatus.PENDING_UPDATE) {

                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = { /* TODO: Reject */ },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Icon(Icons.Default.Close, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reject")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { /* TODO: Approve */ },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (article.status == NewsStatus.PENDING_DELETION) "Confirm" else "Approve")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageNewsPreview() {
    PolnesNewsTheme {
        ManageNewsScreen()
    }
}