package com.kelompok1.polnesnews.model

import com.kelompok1.polnesnews.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Objek singleton untuk menyediakan data palsu (dummy)
 * untuk kebutuhan preview, testing, dan pengembangan awal.
 */
object DummyData {

    /**
     * Helper internal untuk mengubah format tanggal string (yyyy-MM-dd)
     * menjadi format tampilan yang lebih mudah dibaca (Contoh: Tuesday, 04 November 2025).
     */
    fun formatDate(dateString: String): String {
        try {
            // Format input dari data (2025-11-09)
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
            // Format output ke UI (Saturday, 09 November 2025)
            val outputFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH)

            val date = LocalDate.parse(dateString, inputFormatter)
            return date.format(outputFormatter)
        } catch (e: Exception) {
            // Jika format salah/error, kembalikan string aslinya biar gak crash
            return dateString
        }
    }

    // Daftar pengguna dummy
    val userList = listOf(
        User(
            id = 1,
            name = "Ade Darmawan",
            email = "ade@polnesnews.com",
            password = "password123",
            role = UserRole.EDITOR // Menggunakan Constant String dari object UserRole
        ),
        User(
            id = 2,
            name = "Editor B",
            email = "editor_b@polnesnews.com",
            password = "password123",
            role = UserRole.EDITOR
        ),
        User(
            id = 3,
            name = "John Doe",
            email = "johndoe@polnesnews.com",
            password = "password123",
            role = UserRole.USER // Menggunakan Constant String
        ),
        User(
            id = 4,
            name = "test",
            email = "test@t.com",
            password = "test",
            role = UserRole.USER
        ),
        User(
            id = 5,
            name = "e",
            email = "e",
            password = "e",
            role = UserRole.EDITOR
        ),
        User(
            id = 6,
            name = "u",
            email = "u",
            password = "u",
            role = UserRole.USER
        ),
        User(
            id = 7,
            name = "a",
            email = "a",
            password = "a",
            role = UserRole.ADMIN
        )
    )

    // Daftar kategori dummy
    val categoryList = listOf(
        Category(
            id = 1,
            name = "Teknologi",
            // Menggunakan URL string dummy karena model diubah ke String
            imageUrl = "https://ui-avatars.com/api/?name=Teknologi&background=0D8ABC&color=fff"
        ),
        Category(
            id = 2,
            name = "Ekonomi",
            imageUrl = "https://ui-avatars.com/api/?name=Ekonomi&background=2ecc71&color=fff"
        )
    )

    // Daftar berita dummy
    val newsList = listOf(
        News(
            id = 1,
            title = "Inovasi Teknologi Baru di Indonesia",
            categoryId = 1,
            // Menggunakan URL string dummy
            imageUrl = "https://picsum.photos/seed/tech/400/200",
            content = "Konten berita ini <b>hanya contoh</b> untuk tampilan awal.",
            authorId = 1,
            date = "2025-11-09",
            views = 4,
            youtubeVideoId = "dQw4w9WgXcQ",
            status = NewsStatus.PUBLISHED // Menggunakan Constant String dari object NewsStatus
        ),
        News(
            id = 2,
            title = "Ekonomi Dunia Mulai Pulih Pasca Krisis",
            categoryId = 2,
            imageUrl = "https://picsum.photos/seed/economy/400/200",
            content = "Isi berita contoh kedua.",
            authorId = 2,
            date = "2025-11-08",
            views = 12,
            youtubeVideoId = null,
            status = NewsStatus.PENDING_REVIEW // Menggunakan Constant String
        )
    )

    // Daftar komentar/rating dummy
    val commentList = listOf(
        Comment(
            id = 1,
            newsId = 1, // Rating untuk berita "Inovasi Teknologi" (id=1)
            userId = 3, // Diberikan oleh "John Doe" (id=3)
            rating = 5, // Rating 5 bintang
            date = "2025-11-10"
        ),
        Comment(
            id = 2,
            newsId = 1, // Rating lain untuk berita yang sama (id=1)
            userId = 2, // Diberikan oleh "Editor B" (id=2)
            rating = 3, // Rating 3 bintang
            date = "2025-11-11"
        ),
        Comment(
            id = 3,
            newsId = 2, // Rating untuk berita "Ekonomi" (id=2)
            userId = 3, // Diberikan oleh "John Doe" (id=3)
            rating = 2, // Rating 2 bintang
            date = "2025-11-09"
        )
    )

    /**
     * Membuat daftar notifikasi palsu.
     * Data ini diturunkan (di-map) dari 'newsList' di atas.
     * Tujuannya agar data notifikasi selalu sinkron dengan data berita.
     */
    val notifications = newsList.map { news ->
        // Cari nama kategori berdasarkan categoryId dari berita
        val categoryName = categoryList.find { it.id == news.categoryId }?.name ?: "Unknown"

        Notification(
            id = news.id,
            // Model Notification masih pakai Int resource untuk icon,
            // jadi kita biarkan pakai R.drawable (ikon lokal aplikasi)
            iconRes = R.drawable.ic_news, // Pastikan resource ini ada di folder drawable Anda
            category = categoryName,
            title = news.title,
            date = formatDate(news.date) // Gunakan helper format tanggal
        )
    }
}