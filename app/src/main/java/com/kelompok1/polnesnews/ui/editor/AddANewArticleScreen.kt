package com.kelompok1.polnesnews.ui.editor

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kelompok1.polnesnews.components.CommonTopBar // 🟢 Import CommonTopBar
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
    onDeleteClick: () -> Unit // Parameter ini dibiarkan untuk menjaga kompatibilitas navigasi, meski tidak dipakai di UI
) {
    // --- Data Logic ---
    val isEditMode = articleId != null
    val articleToEdit: News? = if (isEditMode) { DummyData.newsList.find { it.id == articleId } } else { null }

    // State Form
    var title by remember { mutableStateOf(articleToEdit?.title ?: "") }
    var content by remember { mutableStateOf(articleToEdit?.content ?: "") }
    var youtubeLink by remember { mutableStateOf(articleToEdit?.youtubeVideoId ?: "") }

    // State Image
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }

    // State Category
    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    val initialCategory = DummyData.categoryList.find { it.id == articleToEdit?.categoryId }
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    val categories = DummyData.categoryList

    Scaffold(
        // Mengatur insets ke 0 agar TopBar bisa menyatu dengan status bar (hijau full)
        contentWindowInsets = WindowInsets(0.dp),

        topBar = {
            // 🟢 MENGGUNAKAN COMMON TOP BAR
            CommonTopBar(
                title = if (isEditMode) "Edit Article" else "Add a New Article",
                onBack = onBackClick,
                // Kita set WindowInsets(0.dp) di sini agar cocok dengan Scaffold
                windowInsets = WindowInsets(0.dp)
                // Actions dikosongkan -> Tombol Hapus Hilang
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
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // --- INPUT JUDUL ---
            Text("Title", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Add title...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // --- INPUT GAMBAR (Style Kotak) ---
            Text("Image", style = MaterialTheme.typography.titleMedium)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 2f) // Ratio 3:2 landscape
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(
                        width = 1.dp,
                        color = Color.Gray.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { launcher.launch("image/*") }
            ) {
                if (selectedImageUri != null) {
                    // 1. Gambar Baru dari Galeri
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Tap to change", color = Color.White, style = MaterialTheme.typography.labelMedium)
                    }

                } else if (isEditMode && articleToEdit != null) {
                    // 2. Gambar Lama (Mode Edit)
                    Image(
                        painter = painterResource(id = articleToEdit.imageRes),
                        contentDescription = "Current Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Tap to change", color = Color.White, style = MaterialTheme.typography.labelMedium)
                    }

                } else {
                    // 3. Kosong (Placeholder)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap to upload image",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // --- INPUT KATEGORI ---
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

            // --- INPUT KONTEN ---
            Text("Text", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("Start writing your article") },
                modifier = Modifier.fillMaxWidth().height(250.dp)
            )

            // --- INPUT VIDEO ---
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