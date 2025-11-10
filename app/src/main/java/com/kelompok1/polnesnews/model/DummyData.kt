package com.kelompok1.polnesnews.model

import com.kelompok1.polnesnews.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DummyData {

    // Format tanggal: yyyy-MM-dd → Tuesday, 04 November 2025
    private fun formatDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(dateString, inputFormatter)
        return date.format(outputFormatter)
    }

    val userList = listOf(
        User(
            id = 1,
            name = "Editor A",
            email = "editor_a@polnesnews.com",
            password = "password123",
            role = UserRole.EDITOR
        ),
        User(
            id = 2,
            name = "Editor B",
            email = "editor_b@polnesnews.com",
            password = "password123",
            role = UserRole.EDITOR
        )
    )

    val categoryList = listOf(
        Category(1, "Teknologi", R.drawable.category_tech),
        Category(2, "Ekonomi", R.drawable.category_economy)
    )

    val newsList = listOf(
        News(
            id = 1,
            title = "Inovasi Teknologi Baru di Indonesia",
            categoryId = 1,
            imageRes = R.drawable.sample_news1,
            content = "Konten berita ini hanya contoh untuk tampilan awal.",
            authorId = 1, // Editor A
            date = "2025-11-09",
            views = 4
        ),
        News(
            id = 2,
            title = "Ekonomi Dunia Mulai Pulih Pasca Krisis",
            categoryId = 2,
            imageRes = R.drawable.sample_news2,
            content = "Isi berita contoh kedua.",
            authorId = 2, // Editor B
            date = "2025-11-08",
            views = 12
        )
    )

    val notifications = newsList.map { news ->
        val categoryName = categoryList.find { it.id == news.categoryId }?.name ?: "Unknown"
        Notification(
            id = news.id,
            iconRes = R.drawable.ic_news,
            category = categoryName,
            title = news.title,
            date = formatDate(news.date)
        )
    }
}
