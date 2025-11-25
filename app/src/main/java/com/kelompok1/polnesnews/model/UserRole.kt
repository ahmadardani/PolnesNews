package com.kelompok1.polnesnews.model

/**
 * Pengganti Enum.
 * Kita gunakan konstanta String agar mudah diterima oleh Database/Backend
 * tapi tetap aman dari Typo saat coding di Android.
 */
object UserRole {
    const val ADMIN = "ADMIN"
    const val EDITOR = "EDITOR"
    const val USER = "USER"
}