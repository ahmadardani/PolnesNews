package com.kelompok1.polnesnews.ui.admin

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.components.AdminBottomNav
import com.kelompok1.polnesnews.components.AdminCategoryCard
import com.kelompok1.polnesnews.components.DeleteConfirmationDialog // 🟢 Import komponen dialog
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.model.Category
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

@Composable
fun ManageCategoriesScreen(
    onAddCategoryClick: () -> Unit
) {
    val categories = DummyData.categoryList
    val context = LocalContext.current

    // State untuk menyimpan kategori yang sedang ingin dihapus
    // Jika null, artinya dialog sedang tidak muncul
    var categoryToDelete by remember { mutableStateOf<Category?>(null) }

    // --- Dialog Konfirmasi Hapus ---
    if (categoryToDelete != null) {
        DeleteConfirmationDialog(
            showDialog = true,
            onDismiss = { categoryToDelete = null }, // Tutup dialog
            onConfirm = {
                // TODO: Logic Hapus Kategori ke Database
                // Simulasi: Cek apakah kategori dipakai
                val isUsed = DummyData.newsList.any { it.categoryId == categoryToDelete?.id }

                if (isUsed) {
                    Toast.makeText(context, "Cannot delete! Category is in use.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Category Deleted: ${categoryToDelete?.name}", Toast.LENGTH_SHORT).show()
                }

                // Tutup dialog setelah konfirmasi
                categoryToDelete = null
            }
        )
    }

    // Layout Utama (Content-Only)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(categories) { category ->
                AdminCategoryCard(
                    category = category,
                    onEditClick = {
                        // TODO: Navigasi ke Edit (dengan ID)
                        // onEditCategory(category.id)
                        Toast.makeText(context, "Edit: ${category.name}", Toast.LENGTH_SHORT).show()
                    },
                    onDeleteClick = {
                        // 🟢 Saat tombol hapus diklik, jangan langsung hapus
                        // Tapi simpan ke state 'categoryToDelete' agar dialog muncul
                        categoryToDelete = category
                    }
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }

        FloatingActionButton(
            onClick = onAddCategoryClick,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Category")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ManageCategoriesFullPreview() {
    PolnesNewsTheme {
        Scaffold(
            topBar = { TitleOnlyTopAppBar(title = "Manage Categories") },
            bottomBar = {
                AdminBottomNav(currentRoute = "Categories", onItemClick = {})
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                ManageCategoriesScreen(onAddCategoryClick = {})
            }
        }
    }
}