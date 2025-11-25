package com.kelompok1.polnesnews.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id")
    val id: Int,

    @SerializedName("news_id")
    val newsId: Int,

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("rating")
    val rating: Int,

    @SerializedName("created_at")
    val date: String
)