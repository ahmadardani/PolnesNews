package com.kelompok1.polnesnews.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image_url")
    val imageUrl: String // UBAH: Dari Int (Resource) ke String (URL)
)