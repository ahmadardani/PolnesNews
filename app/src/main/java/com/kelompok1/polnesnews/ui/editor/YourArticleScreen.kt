package com.kelompok1.polnesnews.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.components.ArticleCard
import com.kelompok1.polnesnews.components.ConfirmationDialog
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.News
import com.kelompok1.polnesnews.model.NewsStatus
import com.kelompok1.polnesnews.model.UserRole
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme
import com.kelompok1.polnesnews.ui.theme.White
import com.kelompok1.polnesnews.ui.theme.ActionDeleteIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourArticleScreen(navController: NavHostController) {
    val currentEditor = DummyData.userList.find { it.role == UserRole.EDITOR && it.id == 1 }
    val editorArticles = DummyData.newsList.filter { it.authorId == currentEditor?.id }

    var articleToDelete by remember { mutableStateOf<News?>(null) }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("article_form")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Article", tint = White)
            }
        }
    ) { innerPadding ->

        if (articleToDelete != null) {
            ConfirmationDialog(
                title = "Hapus Artikel?",
                text = "Apakah Anda yakin ingin mengajukan penghapusan untuk artikel '${articleToDelete?.title}'?",
                confirmButtonColor = ActionDeleteIcon,
                confirmButtonText = "Hapus",
                dismissButtonText = "Batal",
                onDismiss = { articleToDelete = null },
                onConfirm = { articleToDelete = null }
            )
        }

        if (editorArticles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("You haven't written any articles yet.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    // 🟢 PERBAIKAN DI SINI:
                    // Tambahkan + 16.dp agar item pertama (Tanggal) tidak mepet ke Top Bar
                    top = innerPadding.calculateTopPadding() + 16.dp,

                    bottom = innerPadding.calculateBottomPadding(),

                    // Note: Saya ubah start/end jadi 0.dp karena ArticleCard biasanya
                    // sudah punya padding horizontal sendiri biar tidak dobel.
                    start = 0.dp,
                    end = 0.dp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                items(editorArticles) { article ->
                    val (statusMessage, statusColor) = when (article.status) {
                        NewsStatus.PENDING_REVIEW -> "Menunggu Review" to MaterialTheme.colorScheme.tertiary
                        NewsStatus.PENDING_DELETION -> "Menunggu Hapus" to MaterialTheme.colorScheme.error
                        NewsStatus.PENDING_UPDATE -> "Menunggu Edit" to MaterialTheme.colorScheme.secondary
                        NewsStatus.REJECTED -> "Ditolak Admin" to MaterialTheme.colorScheme.error
                        else -> null to Color.Transparent
                    }

                    Box(modifier = Modifier.fillMaxWidth()) {
                        ArticleCard(
                            article = article,
                            onEdit = {
                                if (statusMessage == null || article.status == NewsStatus.DRAFT || article.status == NewsStatus.REJECTED) {
                                    navController.navigate("article_form?articleId=${article.id}")
                                }
                            },
                            onDelete = {
                                if (statusMessage == null || article.status == NewsStatus.DRAFT || article.status == NewsStatus.REJECTED) {
                                    articleToDelete = article
                                }
                            }
                        )

                        if (statusMessage != null) {
                            Surface(
                                color = statusColor.copy(alpha = 0.9f),
                                contentColor = White,
                                shape = MaterialTheme.shapes.medium.copy(
                                    topEnd = androidx.compose.foundation.shape.CornerSize(0.dp),
                                    bottomStart = androidx.compose.foundation.shape.CornerSize(8.dp)
                                ),
                                modifier = Modifier.align(Alignment.TopEnd)
                                    .padding(top = 8.dp, end = 16.dp) // Sesuaikan posisi badge agar pas di card
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (article.status == NewsStatus.REJECTED) Icons.Default.Close else Icons.Default.Info,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp),
                                        tint = White
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = statusMessage,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }
                    // Jarak antar item artikel
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YourArticleScreenPreview() {
    PolnesNewsTheme {
        YourArticleScreen(navController = rememberNavController())
    }
}