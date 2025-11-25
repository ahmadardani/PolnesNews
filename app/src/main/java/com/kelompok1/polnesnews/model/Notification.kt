package com.kelompok1.polnesnews.model

// Notifikasi biasanya data turunan di frontend, jadi tidak harus ada SerializedName
// kecuali notifikasinya memang datang dari database server.
data class Notification(
    val id: Int,
    val iconRes: Int,
    val category: String,
    val title: String,
    val date: String
)