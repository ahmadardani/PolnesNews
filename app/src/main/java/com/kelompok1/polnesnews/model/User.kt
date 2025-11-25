package com.kelompok1.polnesnews.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    // UBAH DARI ENUM KE STRING
    // Default valuenya "USER", jadi kalau backend lupa kirim role, dianggap user biasa.
    @SerializedName("role")
    val role: String = UserRole.USER
)