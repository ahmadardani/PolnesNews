package com.kelompok1.polnesnews.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kelompok1.polnesnews.components.EditorBottomNav
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.UserRole
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme
import com.kelompok1.polnesnews.components.ArticleCard
import com.kelompok1.polnesnews.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourArticleScreen(navController: NavHostController) {
    val currentEditor = DummyData.userList.find { it.role == UserRole.EDITOR && it.id == 1 }
    val editorArticles = DummyData.newsList.filter { it.authorId == currentEditor?.id }

    Scaffold(
        topBar = { TitleOnlyTopAppBar(title = "Your Articles") },
        bottomBar = {
            EditorBottomNav(
                currentRoute = "editor_articles",
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: navigate to AddArticleScreen */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Article", tint = White)
            }
        }
    ) { innerPadding ->
        if (editorArticles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    // 🔹 UBAH 1: Samakan padding horizontal jika kosong
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("You haven't written any articles yet.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                // 🔹 UBAH 2: Hapus padding LazyColumn
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding(),    // Hapus + 12.dp
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 0.dp, // Ubah ke 0.dp
                    end = 0.dp    // Ubah ke 0.dp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                // 🔹 UBAH 3: Hapus verticalArrangement
                // verticalArrangement = Arrangement.spacedBy(12.dp) <-- Hapus/komen baris ini
            ) {
                items(editorArticles) { article ->
                    ArticleCard(
                        article = article,
                        onEdit = { /* TODO: navigate to Edit screen */ },
                        onDelete = { /* TODO: show confirm dialog */ }
                        // Modifier tidak perlu ditambahkan di sini
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YourArticleScreenPreview() {
    PolnesNewsTheme {
        YourArticleScreen(navController = rememberNavController())
    }
}