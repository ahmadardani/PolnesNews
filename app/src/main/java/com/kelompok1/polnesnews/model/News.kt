package com.kelompok1.polnesnews.model

data class News(
    val id: Int,
    val title: String,
    val categoryId: Int,
    val imageRes: Int,
    val content: String,
    val authorId: Int,
    val date: String,
    val views: Int = 0,
    val youtubeVideoId: String? = null
)