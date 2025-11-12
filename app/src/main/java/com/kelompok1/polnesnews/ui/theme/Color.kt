package com.kelompok1.polnesnews.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


val PolnesGreen = Color(0xFF038900)
val PolnesGreenLight = Color(0xFF6DDB6B) // Versi lebih terang untuk Dark Mode
val PolnesGreenDark = Color(0xFF00390A)  // Kontras untuk 'onPrimary' di Dark Mode

//Warna Netral (Bisa disesuaikan)
val LightGray = Color(0xFFF7F7F7)
val DarkGray = Color(0xFF1C1B1F)
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

// --- Warna Khusus Notifikasi ---
val NotifBackgroundLight = Color(0xFFA3E5A6) // Hijau terang untuk latar
val NotifIconTintLight = Color(0xFF50AE5E)   // Hijau gelap untuk ikon

val NotifBackgroundDark = Color(0xFF1B5E20)  // Latar gelap di Dark Mode
val NotifIconTintDark = Color(0xFFC8E6C9)    // Ikon terang di Dark Mode

val LightColorScheme = lightColorScheme(
    primary = PolnesGreen,
    onPrimary = White,
    background = LightGray,
    onBackground = Black,
    surface = White,
    onSurface = Black
)

val DarkColorScheme = darkColorScheme(
    primary = PolnesGreenLight,
    onPrimary = PolnesGreenDark,
    background = Black,
    onBackground = White,
    surface = DarkGray,
    onSurface = White
    /* ... */
)