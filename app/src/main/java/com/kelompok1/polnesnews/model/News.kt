package com.kelompok1.polnesnews.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("category_id")
    val categoryId: Int,

    @SerializedName("image_url")
    val imageUrl: String, // UBAH: Dari Int (Resource) ke String (URL)

    @SerializedName("content")
    val content: String,

    @SerializedName("author_id")
    val authorId: Int,

    @SerializedName("created_at")
    val date: String,

    @SerializedName("views")
    val views: Int,

    @SerializedName("youtube_video_id")
    val youtubeVideoId: String?,

    // Status dibiarkan Enum dulu tidak apa-apa, Gson bisa otomatis baca nama Enumnya
    val status: String = NewsStatus.DRAFT
)