package com.kelompok1.polnesnews.model

/**
 * Pengganti Enum NewsStatus.
 * Menggunakan String agar mudah sinkron dengan database MySQL.
 *
 * Di database nanti kolom 'status' bisa berupa VARCHAR/ENUM('DRAFT', 'PUBLISHED', dll).
 */
object NewsStatus {
    const val DRAFT = "DRAFT"                       // Masih di HP Editor, belum dikirim
    const val PUBLISHED = "PUBLISHED"               // Tayang untuk umum
    const val PENDING_REVIEW = "PENDING_REVIEW"     // Artikel BARU sedang dicek Admin
    const val REJECTED = "REJECTED"                 // Ditolak Admin

    // TAMBAHAN:
    const val PENDING_DELETION = "PENDING_DELETION" // Editor minta hapus
    const val PENDING_UPDATE = "PENDING_UPDATE"     // Editor minta revisi
}