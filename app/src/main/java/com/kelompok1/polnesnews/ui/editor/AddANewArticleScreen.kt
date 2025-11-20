package com.kelompok1.polnesnews.ui.editor // Pastikan package sesuai

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.components.ArticleFormTopBar
import com.kelompok1.polnesnews.components.DeleteConfirmationDialog
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.News
import com.kelompok1.polnesnews.ui.theme.PolnesGreen
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme
import com.kelompok1.polnesnews.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddANewArticleScreen(
    articleId: Int?,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    // --- Data Logic ---
    val isEditMode = articleId != null
    val articleToEdit: News? = if (isEditMode) { DummyData.newsList.find { it.id == articleId } } else { null }
    var title by remember { mutableStateOf(articleToEdit?.title ?: "") }
    var content by remember { mutableStateOf(articleToEdit?.content ?: "") }
    var youtubeLink by remember { mutableStateOf(articleToEdit?.youtubeVideoId ?: "") }
    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    val initialCategory = DummyData.categoryList.find { it.id == articleToEdit?.categoryId }
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    val categories = DummyData.categoryList

    // State Dialog
    var showDeleteDialog by remember { mutableStateOf(false) }

    DeleteConfirmationDialog(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onConfirm = { onDeleteClick() }
    )

    Scaffold(
        // Fix 1: Matikan padding sistem agar TopBar bisa naik ke Status Bar
        contentWindowInsets = WindowInsets(0.dp),

        topBar = {
            ArticleFormTopBar(
                title = if (isEditMode) "Edit Article" else "Add a New Article",
                isEditMode = isEditMode,
                onBackClick = onBackClick,
                onDeleteRequest = { showDeleteDialog = true }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onSubmitClick() },
                containerColor = PolnesGreen,
                contentColor = White
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Submit Article")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    // 🟢 PERBAIKAN DI SINI:
                    // Gunakan calculateTopPadding() agar konten turun ke bawah Top Bar
                    top = innerPadding.calculateTopPadding(),

                    // Padding bawah tetap dinamis (untuk menghindari navigasi gesture HP)
                    bottom = innerPadding.calculateBottomPadding()
                )
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp)) // Tambah jarak sedikit agar tidak terlalu mepet TopBar

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
            OutlinedButton(onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()) {
                Text("Add image or thumbnail")
            }

            // Input Kategori
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
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = categoryDropdownExpanded,
                    onDismissRequest = { categoryDropdownExpanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = { selectedCategory = category; categoryDropdownExpanded = false }
                        )
                    }
                }
            }

            // Input Konten
            Text("Text", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("Start writing your article") },
                modifier = Modifier.fillMaxWidth().height(250.dp)
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

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// --- Preview ---
@Preview(name = "Add Mode", showBackground = true)
@Composable
fun AddArticleScreenPreview() {
    PolnesNewsTheme {
        AddANewArticleScreen(articleId = null, onBackClick = {}, onSubmitClick = {}, onDeleteClick = {})
    }
}

@Preview(name = "Edit Mode", showBackground = true)
@Composable
fun EditArticleScreenPreview() {
    PolnesNewsTheme {
        AddANewArticleScreen(articleId = 1, onBackClick = {}, onSubmitClick = {}, onDeleteClick = {})
    }
}