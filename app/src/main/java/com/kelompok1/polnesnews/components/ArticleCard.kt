package com.kelompok1.polnesnews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
// 🔴 HAPUS baris ini jika ada: import androidx.compose.material.MaterialTheme
// 🔴 HAPUS baris ini jika ada: import androidx.compose.material.Card

// 🟢 PASTIKAN pakai import yang ada angka '3' ini:
import androidx.compose.material3.* import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.News
import com.kelompok1.polnesnews.model.NewsStatus
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
        // 1. Teks Tanggal
        Text(
            text = DummyData.formatDate(article.date),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )

        // 2. Kartu Artikel
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
                    status = article.status // 🟢 Gunakan Status Asli dari Enum
                )

                Spacer(modifier = Modifier.width(16.dp))

                ArticleActions(onEdit = onEdit, onDelete = onDelete)
            }
        }
    }
}

@Composable
private fun ArticleInfo(
    modifier: Modifier = Modifier,
    title: String,
    status: NewsStatus // 🟢 Ubah tipe data jadi NewsStatus
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
        // 🟢 Panggil Komponen StatusChip yang baru (dari file terpisah)
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
        // Tombol Edit
        FilledIconButton(
            onClick = onEdit,
            modifier = Modifier.size(36.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = ActionEditBg, // Pastikan warna ini ada di Theme
                contentColor = ActionEditIcon
            )
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(20.dp))
        }

        // Tombol Delete
        FilledIconButton(
            onClick = onDelete,
            modifier = Modifier.size(36.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = ActionDeleteBg, // Pastikan warna ini ada di Theme
                contentColor = ActionDeleteIcon
            )
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(20.dp))
        }
    }
}

// 🔴 StatusChip LAMA dihapus dari sini karena sudah dipindah ke file baru.
// 🔴 getArticleStatus LAMA dihapus karena kita sudah pakai data enum asli.

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