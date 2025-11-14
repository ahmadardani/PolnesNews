package com.kelompok1.polnesnews.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete // <-- 1. Import Ikon Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.components.CommonTopBar
import com.kelompok1.polnesnews.components.DeleteConfirmationDialog // <-- 2. Import Dialog
import com.kelompok1.polnesnews.model.Category
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.News
import com.kelompok1.polnesnews.ui.theme.PolnesGreen
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme
import com.kelompok1.polnesnews.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddANewArticleScreen(
    // 3. Tambahkan articleId untuk menentukan mode
    articleId: Int?, // null = Mode Add, (angka) = Mode Edit
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit, // Aksi untuk tombol kirim
    onDeleteClick: () -> Unit // Aksi untuk tombol hapus
) {
    // 4. Tentukan mode
    val isEditMode = articleId != null

    // Ambil data artikel jika ini mode edit (untuk mengisi form)
    val articleToEdit: News? = if (isEditMode) {
        DummyData.newsList.find { it.id == articleId }
    } else {
        null
    }

    // State untuk menampung nilai dari setiap input field
    // 5. Isi state dengan data yang ada jika ini mode edit
    var title by remember { mutableStateOf(articleToEdit?.title ?: "") }
    var content by remember { mutableStateOf(articleToEdit?.content ?: "") }
    var youtubeLink by remember { mutableStateOf(articleToEdit?.youtubeVideoId ?: "") }

    // State untuk dropdown kategori
    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    val initialCategory = DummyData.categoryList.find { it.id == articleToEdit?.categoryId }
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    val categories = DummyData.categoryList

    // 6. State untuk dialog konfirmasi hapus
    var showDeleteDialog by remember { mutableStateOf(false) }

    // 7. Panggil Composable Dialog
    DeleteConfirmationDialog(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false }, // Tutup dialog
        onConfirm = {
            onDeleteClick() // Panggil aksi hapus
            // onBackClick() // Opsi: langsung kembali setelah hapus
            // (Kita biarkan onDeleteClick yang menangani navigasi)
        }
    )

    Scaffold(
        topBar = {
            CommonTopBar(
                // 8. Judul dinamis berdasarkan mode
                title = if (isEditMode) "Edit Article" else "Add a New Article",
                onBack = onBackClick,
                // --- 9. INI BAGIAN PENTINGNYA ---
                actions = {
                    // Tampilkan ikon Hapus HANYA jika ini Mode Edit
                    if (isEditMode) {
                        IconButton(onClick = {
                            showDeleteDialog = true // Tampilkan dialog
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Article",
                                tint = White // Sesuaikan warna agar kontras
                            )
                        }
                    }
                }
                // --- AKHIR DARI ACTIONS ---
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // TODO: Kirim data (title, content, selectedCategory?.id, youtubeLink)
                    onSubmitClick()
                },
                containerColor = PolnesGreen,
                contentColor = White
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Submit Article"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Input Judul
            Text("Title", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Add title...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Input Gambar
            Text("Image", style = MaterialTheme.typography.titleMedium)
            OutlinedButton(
                onClick = { /* TODO: Logika image picker */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add image or thumbnail")
            }

            // Input Kategori (Dropdown)
            Text("Category", style = MaterialTheme.typography.titleMedium)
            ExposedDropdownMenuBox(
                expanded = categoryDropdownExpanded,
                onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select Category") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = categoryDropdownExpanded,
                    onDismissRequest = { categoryDropdownExpanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                selectedCategory = category
                                categoryDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Input Konten (Text)
            Text("Text", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("Start writing your article") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
            )

            // Input Video
            Text("Video", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = youtubeLink,
                onValueChange = { youtubeLink = it },
                placeholder = { Text("Add a Youtube Link (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Spacer di bawah agar tidak tertutup FAB
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// --- Preview ---

@Preview(name = "Add Mode", showBackground = true)
@Composable
fun AddArticleScreenPreview() {
    PolnesNewsTheme {
        AddANewArticleScreen(
            articleId = null, // <-- Mode Add
            onBackClick = {},
            onSubmitClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(name = "Edit Mode", showBackground = true)
@Composable
fun EditArticleScreenPreview() {
    PolnesNewsTheme {
        AddANewArticleScreen(
            articleId = 1, // <-- Mode Edit (ambil ID 1 dari DummyData)
            onBackClick = {},
            onSubmitClick = {},
            onDeleteClick = {}
        )
    }
}