package com.kelompok1.polnesnews.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

    // 2. Data Mentah (Semua user kecuali Admin sendiri)
    val allNonAdminUsers = remember { DummyData.userList.filter { it.role != UserRole.ADMIN } }

    // 3. Logika Filter Data berdasarkan Tab yang dipilih
    val displayedUsers = when (selectedTabIndex) {
        0 -> allNonAdminUsers // Tampilkan Semua
        1 -> allNonAdminUsers.filter { it.role == UserRole.USER } // Filter User
        2 -> allNonAdminUsers.filter { it.role == UserRole.EDITOR } // Filter Editor
        else -> emptyList()
    }

    // Layout Utama (Column karena ada Tab di atas dan List di bawah)
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
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        // --- LIST CONTENT ---
        if (displayedUsers.isEmpty()) {
            // Tampilan jika kosong
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No users found in this category.",
                    color = Color.Gray
                )
            }
        } else {
            // Daftar User
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
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

                // Spacer bawah agar item terakhir tidak tertutup Nav Bar di HP layar kecil
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