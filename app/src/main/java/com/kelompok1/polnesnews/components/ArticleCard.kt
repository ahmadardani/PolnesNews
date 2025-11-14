package com.kelompok1.polnesnews.components

// import androidx.compose.foundation.Image <-- Dihapus karena tidak terpakai
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// import androidx.compose.ui.draw.clip <-- Dihapus karena tidak terpakai
import androidx.compose.ui.graphics.Color
// import androidx.compose.ui.layout.ContentScale <-- Dihapus karena tidak terpakai
// import androidx.compose.ui.res.painterResource <-- Dihapus karena tidak terpakai
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.model.News
import com.kelompok1.polnesnews.ui.theme.*
import androidx.compose.ui.tooling.preview.Preview
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme
import androidx.compose.foundation.layout.Arrangement

/**
 * Composable utama untuk menampilkan kartu artikel editor.
 * Versi ini HANYA menampilkan Info dan Tombol Aksi (Tanpa Gambar).
 */
@Composable
fun ArticleCard(
    article: News,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 2. Info (Title & Status)
            ArticleInfo(
                modifier = Modifier.weight(1f),
                title = article.title,
                status = getArticleStatus(article.id)
            )

            Spacer(modifier = Modifier.width(8.dp)) // Jarak antara Info dan Tombol

            // 3. Tombol Aksi (Edit & Delete)
            ArticleActions(onEdit = onEdit, onDelete = onDelete)
        }
    }
}

/**
 * 🔹 FUNGSI ArticleThumbnail DIHAPUS SELURUHNYA 🔹
 * (karena sudah tidak dipanggil lagi)
 */

/**
 * Composable private untuk menampilkan Judul dan StatusChip.
 */
@Composable
private fun ArticleInfo(
    modifier: Modifier = Modifier,
    title: String,
    status: String
) {
    // 🔹 UBAH DI SINI: Samakan dengan gaya ArticleActions
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp) // <-- TAMBAHKAN INI
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2
        )
        // Spacer(modifier = Modifier.height(4.dp)) // <-- HAPUS SPACER MANUAL
        StatusChip(status = status) // Status chip
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
        verticalArrangement = Arrangement.spacedBy(8.dp) // <-- Jarak referensi
    ) {
        // Tombol Edit
        FilledIconButton(
            onClick = onEdit,
            modifier = Modifier.size(36.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = ActionEditBg,   // Latar Kuning Pucat
                contentColor = ActionEditIcon  // Ikon Kuning Tua
            )
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
        }

        // Tombol Delete
        FilledIconButton(
            onClick = onDelete,
            modifier = Modifier.size(36.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = ActionDeleteBg,   // Latar Orange Pucat
                contentColor = ActionDeleteIcon  // Ikon Orange Tua
            )
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
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
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
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
        if (sampleNews != null) {
            ArticleCard(
                article = sampleNews,
                onEdit = {},
                onDelete = {}
            )
        } else {
            Text("Tidak ada data untuk preview")
        }
    }
}

@Preview(showBackground = true, name = "Status Chips")
@Composable
private fun StatusChipPreview() {
    PolnesNewsTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            StatusChip(status = "Published")
            StatusChip(status = "Pending")
            StatusChip(status = "Draft")
        }
    }
}