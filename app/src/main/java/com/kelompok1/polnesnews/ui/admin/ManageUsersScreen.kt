package com.kelompok1.polnesnews.ui.admin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok1.polnesnews.components.AdminBottomNav
import com.kelompok1.polnesnews.components.AdminUserCard
import com.kelompok1.polnesnews.components.DeleteConfirmationDialog
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.User
import com.kelompok1.polnesnews.model.UserRole
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

@Composable
fun ManageUsersScreen() {
    val context = LocalContext.current

    // 1. State Data User (Menggunakan mutableStateListOf agar UI update saat diedit/hapus)
    // Kita ambil data awal dari DummyData, tapi kita filter Admin agar tidak bisa diedit
    val usersState = remember {
        mutableStateListOf<User>().apply {
            addAll(DummyData.userList.filter { it.role != UserRole.ADMIN })
        }
    }

    // 2. State untuk UI Control
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("All", "User", "Editor")
    var searchQuery by remember { mutableStateOf("") }

    // 3. State untuk Dialog Edit & Hapus
    var userToDelete by remember { mutableStateOf<User?>(null) } // Jika tidak null, dialog hapus muncul
    var userToEdit by remember { mutableStateOf<User?>(null) }   // Jika tidak null, dialog edit role muncul

    // 4. Logika Filter (Tab + Search)
    val displayedUsers = remember(selectedTabIndex, searchQuery, usersState.toList()) {
        // Filter Tab
        val byRole = when (selectedTabIndex) {
            0 -> usersState // All
            1 -> usersState.filter { it.role == UserRole.USER } // User only
            2 -> usersState.filter { it.role == UserRole.EDITOR } // Editor only
            else -> usersState
        }
        // Filter Search
        if (searchQuery.isBlank()) byRole
        else byRole.filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }
    }

    // --- DIALOG KONFIRMASI HAPUS ---
    if (userToDelete != null) {
        DeleteConfirmationDialog(
            showDialog = true,
            onDismiss = { userToDelete = null },
            onConfirm = {
                // Logika Hapus dari List
                usersState.remove(userToDelete)
                Toast.makeText(context, "User ${userToDelete?.name} deleted", Toast.LENGTH_SHORT).show()
                userToDelete = null
            }
        )
    }

    // --- DIALOG EDIT ROLE (PROMOTE/DEMOTE) ---
    if (userToEdit != null) {
        EditUserRoleDialog(
            user = userToEdit!!,
            onDismiss = { userToEdit = null },
            onSave = { newRole ->
                // Logika Update Role
                val index = usersState.indexOfFirst { it.id == userToEdit!!.id }
                if (index != -1) {
                    // Update item di dalam list state
                    val updatedUser = usersState[index].copy(role = newRole)
                    usersState[index] = updatedUser
                    Toast.makeText(context, "Role updated to ${newRole.name}", Toast.LENGTH_SHORT).show()
                }
                userToEdit = null
            }
        )
    }

    // --- LAYOUT UTAMA ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Tab Row
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

        // Search Bar (Background Putih)
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
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    // ✅ BACKGROUND PUTIH SOLID
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    // Border Colors
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        // List User
        if (displayedUsers.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No users found.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(displayedUsers) { user ->
                    AdminUserCard(
                        user = user,
                        onEditClick = {
                            // Trigger Dialog Edit Role
                            userToEdit = user
                        },
                        onDeleteClick = {
                            // Trigger Dialog Hapus
                            userToDelete = user
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

/**
 * Dialog Khusus untuk Mengubah Role User (User <-> Editor)
 */
@Composable
fun EditUserRoleDialog(
    user: User,
    onDismiss: () -> Unit,
    onSave: (UserRole) -> Unit
) {
    var selectedRole by remember { mutableStateOf(user.role) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Role for ${user.name}") },
        text = {
            Column {
                Text("Select new role:", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))

                // Pilihan Role: User & Editor (Admin tidak ditampilkan karena ini manage users)
                listOf(UserRole.USER, UserRole.EDITOR).forEach { role ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (role == selectedRole),
                                onClick = { selectedRole = role },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (role == selectedRole),
                            onClick = null // Ditangani oleh Row selectable
                        )
                        Text(
                            text = role.name, // Menampilkan "USER" atau "EDITOR"
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onSave(selectedRole) }) {
                Text("Save Changes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// --- PREVIEW ---
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ManageUsersFullPreview() {
    PolnesNewsTheme {
        Scaffold(
            topBar = { TitleOnlyTopAppBar(title = "Manage Users") },
            bottomBar = { AdminBottomNav(currentRoute = "Users", onItemClick = {}) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                ManageUsersScreen()
            }
        }
    }
}