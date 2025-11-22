package com.kelompok1.polnesnews.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok1.polnesnews.components.AccountInfoCard
import com.kelompok1.polnesnews.components.AdminBottomNav // Import BottomNav Admin
import com.kelompok1.polnesnews.components.SettingsButton
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar // Import TopBar
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.User
import com.kelompok1.polnesnews.model.UserRole
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

@Composable
fun AdminSettingsScreen(
    currentUser: User?,
    onLogout: () -> Unit
) {
    // Halaman ini CONTENT ONLY (Tanpa Scaffold), karena Scaffold ada di NavGraph
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        // --- Info Akun ---
        Text(
            text = "Account",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        AccountInfoCard(
            fullName = currentUser?.name ?: "Admin",
            role = "Administrator"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Pengaturan Sistem (Khusus Admin) ---
        Text(
            text = "System Settings",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        SettingsButton(
            text = "App Configuration",
            icon = Icons.Outlined.SettingsSuggest,
            onClick = { /* TODO */ }
        )
        SettingsButton(
            text = "Audit Logs",
            icon = Icons.Outlined.History,
            onClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Pengaturan Umum ---
        Text(
            text = "General",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        SettingsButton(
            text = "Privacy and Policy",
            icon = Icons.Outlined.PrivacyTip,
            onClick = { /* TODO */ }
        )
        SettingsButton(
            text = "About App",
            icon = Icons.Outlined.Info,
            onClick = { /* TODO */ }
        )

        // Tombol Logout
        SettingsButton(
            text = "Logout",
            icon = Icons.Outlined.Logout,
            onClick = onLogout
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// --- PREVIEW LENGKAP (DENGAN TOP & BOTTOM BAR) ---
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Full Screen Preview")
@Composable
private fun AdminSettingsFullPreview() {
    PolnesNewsTheme {
        val mockAdmin = DummyData.userList.find { it.role == UserRole.ADMIN }

        // Kita buat Scaffold BOHONGAN hanya untuk Preview
        // Agar kita bisa lihat posisi TopBar dan BottomBar-nya
        Scaffold(
            topBar = {
                TitleOnlyTopAppBar(title = "Settings")
            },
            bottomBar = {
                AdminBottomNav(
                    currentRoute = "Settings", // Ceritanya kita sedang di tab Settings
                    onItemClick = {}
                )
            }
        ) { innerPadding ->
            // Masukkan konten screen kita ke dalam padding Scaffold preview
            Box(modifier = Modifier.padding(innerPadding)) {
                AdminSettingsScreen(
                    currentUser = mockAdmin,
                    onLogout = {}
                )
            }
        }
    }
}