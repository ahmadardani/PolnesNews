package com.kelompok1.polnesnews.model

import com.kelompok1.polnesnews.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DummyData {

    // Fungsi untuk format tanggal
    private fun formatDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(dateString, inputFormatter)
        return date.format(outputFormatter)
    }

    val newsList = listOf(
        News(
            id = 1,
            title = "Inovasi Teknologi Baru di Indonesia",
            category = "Teknologi",
            imageRes = R.drawable.sample_news1,
            content = "Konten berita ini hanya contoh untuk tampilan awal.",
            author = "Admin",
            date = "2025-11-09"
        ),
        News(
            id = 2,
            title = "Ekonomi Dunia Mulai Pulih Pasca Krisis",
            category = "Ekonomi",
            imageRes = R.drawable.sample_news2,
            content = "Isi berita contoh kedua.",
            author = "Editor",
            date = "2025-11-08"
        )
    )

    val categoryList = listOf(
        Category(1, "Technology", R.drawable.category_tech)
    )

    // Notifikasi otomatis berdasarkan newsList
    val notifications = newsList.map { news ->
        Notification(
            id = news.id,
            iconRes = R.drawable.ic_news,
            category = news.category,
            title = news.title,
            date = formatDate(news.date) // Format langsung
        )
    }
}
