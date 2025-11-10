package com.kelompok1.polnesnews.model

data class News(
    val id: Int,
    val title: String,
    val category: String,
    val imageRes: Int, // gunakan resource drawable lokal
    val content: String,
    val author: String,
    val date: String,
    val views: Int = 0,
    val isFeatured: Boolean = false
)