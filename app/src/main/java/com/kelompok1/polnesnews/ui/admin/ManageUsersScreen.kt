package com.kelompok1.polnesnews.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.components.AdminBottomNav
import com.kelompok1.polnesnews.components.AdminUserCard
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.UserRole
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

@Composable
fun ManageUsersScreen() {
    // 1. State untuk Tab (0=All, 1=User, 2=Editor)
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("All", "User", "Editor")

    // 2. State untuk Search Query
    var searchQuery by remember { mutableStateOf("") }

    // 3. Data Mentah (Semua user kecuali Admin sendiri)
    val allNonAdminUsers = remember { DummyData.userList.filter { it.role != UserRole.ADMIN } }

    // 4. Logika Filter: Berdasarkan TAB -> lalu berdasarkan SEARCH
    val displayedUsers = remember(selectedTabIndex, searchQuery) {
        // Langkah A: Filter by Role (Tab)
        val usersByRole = when (selectedTabIndex) {
            0 -> allNonAdminUsers // All
            1 -> allNonAdminUsers.filter { it.role == UserRole.USER } // User
            2 -> allNonAdminUsers.filter { it.role == UserRole.EDITOR } // Editor
            else -> emptyList()
        }

        // Langkah B: Filter by Search Query (Name or Username)
        if (searchQuery.isBlank()) {
            usersByRole
        } else {
            usersByRole.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    // Layout Utama
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // --- TAB ROW ---
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        // Opsional: Reset search saat ganti tab jika diinginkan
                        // searchQuery = ""
                    },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        // --- SEARCH BAR ---
        PaddingValues(16.dp).let { padding ->
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search by name...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear search")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                singleLine = true,
                shape = RoundedCornerShape(24.dp), // Membuat search bar bulat
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        // --- LIST CONTENT ---
        if (displayedUsers.isEmpty()) {
            // Tampilan jika kosong
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (searchQuery.isNotEmpty()) "No users found for \"$searchQuery\"" else "No users available.",
                        color = Color.Gray
                    )
                }
            }
        } else {
            // Daftar User
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp), // Padding disesuaikan
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(displayedUsers) { user ->
                    AdminUserCard(
                        user = user,
                        onEditClick = {
                            // TODO: Navigasi ke edit user
                        },
                        onDeleteClick = {
                            // TODO: Implementasi logika hapus user
                        }
                    )
                }

                // Spacer bawah agar item terakhir tidak tertutup Nav Bar
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

// --- PREVIEW LENGKAP ---
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ManageUsersFullPreview() {
    PolnesNewsTheme {
        Scaffold(
            topBar = {
                TitleOnlyTopAppBar(title = "Manage Users")
            },
            bottomBar = {
                AdminBottomNav(
                    currentRoute = "Users",
                    onItemClick = {}
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                ManageUsersScreen()
            }
        }
    }
}