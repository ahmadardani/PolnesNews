package com.kelompok1.polnesnews.model

data class Notification(
    val id: Int,
    val iconRes: Int,
    val category: String,
    val title: String,
    val date: String
)
