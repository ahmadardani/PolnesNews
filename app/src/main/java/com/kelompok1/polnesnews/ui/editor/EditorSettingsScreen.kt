package com.kelompok1.polnesnews.ui.editor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kelompok1.polnesnews.components.AccountInfoCard
import com.kelompok1.polnesnews.components.EditorBottomNav
import com.kelompok1.polnesnews.components.SettingsButton
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

// 🔴 Import SessionManager DIHAPUS

@Composable
fun EditorSettingsScreen(
    navController: NavHostController,
    onLogout: () -> Unit,
    onPrivacyClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    // 🟢 REVISI: Hapus pengambilan user dari SessionManager
    // val currentUser = SessionManager.currentUser // REMOVED

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        // --- Bagian Info Akun ---
        Text(
            text = "Account",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // 🟢 Menggunakan Placeholder
        AccountInfoCard(
            fullName = "Ade Darmawan (Editor)",
            role = "Editor"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Bagian Tombol-Tombol Pengaturan (Tetap Sama) ---
        Text(
            text = "More Settings",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        SettingsButton(
            text = "Privacy and Policy",
            icon = Icons.Outlined.PrivacyTip,
            onClick = onPrivacyClick
        )
        SettingsButton(
            text = "About",
            icon = Icons.Outlined.Info,
            onClick = onAboutClick
        )

        // Tombol Logout
        SettingsButton(
            text = "Logout",
            icon = Icons.Outlined.Logout,
            onClick = onLogout
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// --- PREVIEW ---
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun EditorSettingsScreenPreview() {
    PolnesNewsTheme {
        Scaffold(
            topBar = { TitleOnlyTopAppBar(title = "Settings") },
            bottomBar = { EditorBottomNav(currentRoute = "editor_settings", onNavigate = {}) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                EditorSettingsScreen(
                    navController = rememberNavController(),
                    onLogout = {},
                    onPrivacyClick = {},
                    onAboutClick = {}
                )
            }
        }
    }
}