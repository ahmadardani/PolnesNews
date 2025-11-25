package com.kelompok1.polnesnews.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok1.polnesnews.components.AccountInfoCard
import com.kelompok1.polnesnews.components.SettingsButton
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.components.UserBottomNav

// 🔴 SessionManager Import SUDAH DIHAPUS

@Composable
fun SettingsScreen(
    onLogout: () -> Unit,
    onPrivacyClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    // 🟢 REVISI: Karena SessionManager dihapus, logika pengambilan user juga dihapus.
    // Nanti Backend Developer yang akan memasang logika pengambilan data profil di sini.

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

        // 🟢 Menampilkan Placeholder (Guest) agar UI tidak error
        AccountInfoCard(
            fullName = "Guest User", // TODO: Nanti diganti dengan nama dari Backend
            role = "User"            // TODO: Nanti diganti dengan role dari Backend
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Bagian Tombol-Tombol Pengaturan ---
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
    }
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(
        onLogout = {},
        onPrivacyClick = {},
        onAboutClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun FullSettingsScreenPreview() {
    Scaffold(
        topBar = { TitleOnlyTopAppBar(title = "Settings") },
        bottomBar = { UserBottomNav(currentRoute = "Settings", onItemClick = {}) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            SettingsScreen(
                onLogout = {},
                onPrivacyClick = {},
                onAboutClick = {}
            )
        }
    }
}