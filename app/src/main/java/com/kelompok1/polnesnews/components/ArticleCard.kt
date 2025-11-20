package com.kelompok1.polnesnews.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.News
import com.kelompok1.polnesnews.ui.theme.*


@Composable
fun ArticleCard(
    article: News,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // 2. Teks Tanggal (SUDAH DIFORMAT)
        Text(
            // 🟢 PANGGIL FUNGSI FORMATTER DI SINI
            text = DummyData.formatDate(article.date),

            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )

        // 3. Kartu Artikel
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ArticleInfo(
                    modifier = Modifier.weight(1f),
                    title = article.title,
                    status = getArticleStatus(article.id)
                )

                Spacer(modifier = Modifier.width(16.dp))

                ArticleActions(onEdit = onEdit, onDelete = onDelete)
            }
        }
    }
}

// ... (Sisa kode ArticleInfo, ArticleActions, StatusChip, dll TETAP SAMA) ...

@Composable
private fun ArticleInfo(
    modifier: Modifier = Modifier,
    title: String,
    status: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2
        )
        StatusChip(status = status)
    }
}

@Composable
private fun ArticleActions(
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilledIconButton(
            onClick = onEdit,
            modifier = Modifier.size(36.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = ActionEditBg,
                contentColor = ActionEditIcon
            )
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(20.dp))
        }

        FilledIconButton(
            onClick = onDelete,
            modifier = Modifier.size(36.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = ActionDeleteBg,
                contentColor = ActionDeleteIcon
            )
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val (bgColor, textColor) = when (status) {
        "Draft" -> Pair(StatusDraftBg, StatusDraftText)
        "Published" -> Pair(StatusPublishedBg, StatusPublishedText)
        "Pending" -> Pair(StatusPendingBg, StatusPendingText)
        else -> Pair(Color.Gray.copy(alpha = 0.2f), Color.DarkGray)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = status,
            color = textColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

private fun getArticleStatus(newsId: Int): String {
    return when (newsId % 3) {
        0 -> "Draft"
        1 -> "Published"
        else -> "Pending"
    }
}

@Preview(showBackground = true, name = "Article Card Preview")
@Composable
private fun ArticleCardPreview() {
    val sampleNews = DummyData.newsList.firstOrNull()
    PolnesNewsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (sampleNews != null) {
                ArticleCard(article = sampleNews, onEdit = {}, onDelete = {})
            }
        }
    }
}