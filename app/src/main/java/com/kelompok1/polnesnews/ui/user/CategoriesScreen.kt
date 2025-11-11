package com.kelompok1.polnesnews.ui.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.components.CategoryCard
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.components.UserBottomNav
import com.kelompok1.polnesnews.model.DummyData

/**
 * Layar yang menampilkan daftar semua kategori berita.
 */
@Composable
fun CategoriesScreen() {
    val categories = DummyData.categoryList

    // Gunakan LazyColumn untuk menampilkan daftar kategori
    // secara vertikal (satu item per baris).
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        // Beri padding di dalam area scroll, di sekeliling semua item.
        contentPadding = PaddingValues(8.dp)
    ) {
        // Render 'CategoryCard' untuk setiap item di dalam list 'categories'
        items(categories) { category ->
            CategoryCard(
                category = category,
                onClick = {
                    // TODO: Implementasi navigasi ke layar detail kategori
                    // (Misal: "CategoryNewsList/${category.id}")
                }
            )
        }
    }
}

// --- Preview 1: Hanya konten (Default) ---
@Preview(showBackground = true, name = "Hanya Konten (Default)")
@Composable
private fun CategoriesScreenPreview() {
    // PolnesNewsTheme {
    // Preview ini hanya merender konten layarnya saja.
    CategoriesScreen()
    // }
}

// --- Preview 2: Tampilan Penuh (Full App) ---
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Tampilan Penuh (Full App)")
@Composable
private fun FullCategoriesScreenPreview() {
    // PolnesNewsTheme {
    // Preview ini mensimulasikan bagaimana layar ini akan terlihat
    // lengkap dengan TopBar dan BottomNav di dalam aplikasi.
    Scaffold(
        topBar = {
            TitleOnlyTopAppBar(title = "Categories")
        },
        bottomBar = {
            UserBottomNav(
                currentRoute = "Categories", // Tandai 'Categories' sebagai aktif
                onItemClick = {} // Tidak perlu aksi di preview
            )
        }
    ) { innerPadding ->
        // Terapkan padding dari Scaffold ke konten kita
        Box(modifier = Modifier.padding(innerPadding)) {
            CategoriesScreen()
        }
    }
    // }
}