package com.kelompok1.polnesnews.ui.editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.ListAlt
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
import com.kelompok1.polnesnews.components.EditorBottomNav // Asumsi Anda punya ini
import com.kelompok1.polnesnews.components.SettingsButton
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.User
import com.kelompok1.polnesnews.model.UserRole
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

/**
 * Layar Pengaturan (Settings) khusus untuk Editor.
 * Menampilkan info akun dan navigasi khusus editor.
 */
@Composable
fun EditorSettingsScreen(
    currentUser: User?,
    onLogout: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToMyArticles: () -> Unit,
    // Tambahkan lambda navigasi lain jika perlu
) {
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

        AccountInfoCard(
            fullName = currentUser?.name ?: "Editor Not Found",
            role = currentUser?.role?.name?.let {
                it.lowercase().replaceFirstChar { char -> char.uppercase() }
            } ?: "Guest"
        )

        Spacer(modifier = Modifier.height(24.dp)) // Jarak antar seksi

        // --- Bagian Editor Tools ---
        Text(
            text = "Editor Tools",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        SettingsButton(
            text = "Dashboard",
            icon = Icons.Outlined.Dashboard,
            onClick = onNavigateToDashboard
        )
        SettingsButton(
            text = "My Articles",
            icon = Icons.Outlined.ListAlt,
            onClick = onNavigateToMyArticles
        )

        Spacer(modifier = Modifier.height(24.dp)) // Jarak antar seksi

        // --- Bagian Tombol-Tombol Pengaturan ---
        Text(
            text = "More Settings",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        SettingsButton(
            text = "Help and Feedback",
            icon = Icons.Outlined.Help,
            onClick = { /* TODO: Handle Klik */ }
        )
        SettingsButton(
            text = "Privacy and Policy",
            icon = Icons.Outlined.PrivacyTip,
            onClick = { /* TODO: Handle Klik */ }
        )
        SettingsButton(
            text = "About",
            icon = Icons.Outlined.Info,
            onClick = { /* TODO: Handle Klik */ }
        )

        // Tombol Logout
        SettingsButton(
            text = "Logout",
            icon = Icons.Outlined.Logout,
            onClick = onLogout
        )
    }
}

// --- PREVIEW 1: HANYA KONTEN ---
@Preview(showBackground = true, name = "Editor Settings Content")
@Composable
private fun EditorSettingsScreenPreview() {
    // PolnesNewsTheme {
    val editorUser = DummyData.userList.find { it.role == UserRole.EDITOR }
    EditorSettingsScreen(
        currentUser = editorUser,
        onLogout = {},
        onNavigateToDashboard = {},
        onNavigateToMyArticles = {}
    )
    // }
}

// --- PREVIEW 2: TAMPILAN PENUH ---
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Full Editor Settings Screen")
@Composable
private fun FullEditorSettingsScreenPreview() {
    PolnesNewsTheme {
    val editorUser = DummyData.userList.find { it.role == UserRole.EDITOR }

    // Asumsi Anda memiliki TopAppBar dan EditorBottomNav
    Scaffold(
        topBar = {
            TitleOnlyTopAppBar(title = "Settings")
        },
        bottomBar = {
            EditorBottomNav(
                currentRoute = "editor_settings", // 1. Sesuaikan dengan 'route' di NavItem
                onNavigate = {}                 // 2. Ganti nama parameter
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            EditorSettingsScreen(
                currentUser = editorUser,
                onLogout = {},
                onNavigateToDashboard = {},
                onNavigateToMyArticles = {}
            )
        }
    }
    }
}