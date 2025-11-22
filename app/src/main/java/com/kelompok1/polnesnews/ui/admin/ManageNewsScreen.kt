package com.kelompok1.polnesnews.ui.admin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kelompok1.polnesnews.components.DeleteConfirmationDialog
import com.kelompok1.polnesnews.components.StatusChip
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.News
import com.kelompok1.polnesnews.model.NewsStatus
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

// Helper untuk cari nama author
private fun getAuthorName(authorId: Int): String {
    return DummyData.userList.find { it.id == authorId }?.name ?: "Unknown Author"
}

@Composable
fun ManageNewsScreen() {
    val context = LocalContext.current

    // 1. Data Source
    val newsList = remember { mutableStateListOf<News>().apply { addAll(DummyData.newsList) } }

    // 2. UI State
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Needs Review", "All News")
    var searchQuery by remember { mutableStateOf("") }

    // 3. Pagination State
    var itemsToShow by remember { mutableIntStateOf(10) }

    // 4. Dialog States
    var articleToDelete by remember { mutableStateOf<News?>(null) }
    var articleToReview by remember { mutableStateOf<News?>(null) }

    // Reset pagination saat Tab atau Search berubah
    LaunchedEffect(selectedTabIndex, searchQuery) {
        itemsToShow = 10
    }

    // 5. Filter Logic
    val filteredList = remember(selectedTabIndex, searchQuery, newsList.toList()) {
        val list = if (selectedTabIndex == 0) {
            // Needs Review
            newsList.filter {
                it.status == NewsStatus.PENDING_REVIEW ||
                        it.status == NewsStatus.PENDING_DELETION ||
                        it.status == NewsStatus.PENDING_UPDATE
            }
        } else {
            // All News
            if (searchQuery.isBlank()) newsList
            else newsList.filter {
                val authorName = getAuthorName(it.authorId)
                it.title.contains(searchQuery, ignoreCase = true) ||
                        authorName.contains(searchQuery, ignoreCase = true)
            }
        }
        list
    }

    // 6. Pagination Logic
    val paginatedList = filteredList.take(itemsToShow)
    val hasMoreData = filteredList.size > itemsToShow

    // --- DIALOGS ---
    if (articleToDelete != null) {
        DeleteConfirmationDialog(
            showDialog = true,
            onDismiss = { articleToDelete = null },
            onConfirm = {
                newsList.remove(articleToDelete)
                Toast.makeText(context, "Article Deleted", Toast.LENGTH_SHORT).show()
                articleToDelete = null
            }
        )
    }

    if (articleToReview != null) {
        ReviewArticleDialog(
            article = articleToReview!!,
            onDismiss = { articleToReview = null },
            onApprove = {
                val index = newsList.indexOfFirst { it.id == articleToReview!!.id }
                if (index != -1) {
                    if (articleToReview!!.status == NewsStatus.PENDING_DELETION) {
                        newsList.removeAt(index)
                    } else {
                        newsList[index] = newsList[index].copy(status = NewsStatus.PUBLISHED)
                    }
                    Toast.makeText(context, "Request Approved", Toast.LENGTH_SHORT).show()
                }
                articleToReview = null
            },
            onReject = {
                val index = newsList.indexOfFirst { it.id == articleToReview!!.id }
                if (index != -1) {
                    newsList[index] = newsList[index].copy(status = NewsStatus.REJECTED)
                    Toast.makeText(context, "Request Rejected", Toast.LENGTH_SHORT).show()
                }
                articleToReview = null
            }
        )
    }

    // --- MAIN UI ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTabIndex == index) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                )
            }
        }

        // Search Bar
        if (selectedTabIndex == 1) {
            PaddingValues(16.dp).let {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search news title...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) { Icon(Icons.Default.Close, null) }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        // List Content
        if (paginatedList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Article, null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                    Text("No data found.", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(paginatedList) { article ->
                    AdminNewsItem(
                        article = article,
                        isReviewMode = (selectedTabIndex == 0),
                        onActionClick = {
                            if (selectedTabIndex == 0) articleToReview = article
                            else Toast.makeText(context, "Edit soon", Toast.LENGTH_SHORT).show()
                        },
                        onDeleteClick = { articleToDelete = article }
                    )
                }

                // Pagination Button
                if (hasMoreData) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            TextButton(onClick = { itemsToShow += 10 }) {
                                Text("Load More (${filteredList.size - itemsToShow} remaining)")
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }
    }
}

@Composable
fun AdminNewsItem(
    article: News,
    isReviewMode: Boolean,
    onActionClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val authorName = getAuthorName(article.authorId)

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { if (isReviewMode) onActionClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            // 1. Gambar
            Image(
                painter = painterResource(id = article.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 2. Kolom Informasi (Layout Baru: Judul -> Tanggal -> Penulis -> Status)
            Column(modifier = Modifier.weight(1f)) {

                // A. Judul
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                // B. Tanggal
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DateRange, null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = DummyData.formatDate(article.date),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // C. Penulis
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = authorName,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // D. Status (Sekarang di paling bawah)
                StatusChip(status = article.status)
            }

            // 3. Tombol Aksi
            if (isReviewMode) {
                IconButton(
                    onClick = onActionClick,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(Icons.Default.ChevronRight, contentDescription = "Review", tint = MaterialTheme.colorScheme.primary)
                }
            } else {
                Column {
                    IconButton(onClick = onActionClick, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.secondary)
                    }
                    IconButton(onClick = onDeleteClick, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewArticleDialog(
    article: News,
    onDismiss: () -> Unit,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    val authorName = getAuthorName(article.authorId)
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Card(
            modifier = Modifier.fillMaxWidth(0.9f).fillMaxHeight(0.85f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.height(200.dp).fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = article.imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                            .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(50))
                    ) { Icon(Icons.Default.Close, null, tint = Color.White) }
                }
                Column(
                    modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(16.dp)
                ) {
                    val reqColor = if (article.status == NewsStatus.PENDING_DELETION) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    val reqText = when(article.status) {
                        NewsStatus.PENDING_DELETION -> "REQUEST: DELETION"
                        NewsStatus.PENDING_UPDATE -> "REQUEST: UPDATE"
                        else -> "REQUEST: PUBLISH"
                    }
                    Text(reqText, style = MaterialTheme.typography.labelLarge, color = reqColor, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(article.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("By $authorName • ${DummyData.formatDate(article.date)}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Divider(modifier = Modifier.padding(vertical = 16.dp))
                    Text(article.content, style = MaterialTheme.typography.bodyMedium, lineHeight = 24.sp)
                }
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(onClick = onReject, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)) { Text("Reject") }
                    Button(onClick = onApprove, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = if (article.status == NewsStatus.PENDING_DELETION) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)) { Text(if (article.status == NewsStatus.PENDING_DELETION) "Confirm Delete" else "Approve") }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageNewsScreenPreview() {
    PolnesNewsTheme {
        ManageNewsScreen()
    }
}