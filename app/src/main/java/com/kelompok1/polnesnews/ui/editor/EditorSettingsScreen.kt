package com.kelompok1.polnesnews.ui.editor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Help
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
import com.kelompok1.polnesnews.components.SettingsButton
import com.kelompok1.polnesnews.components.TitleOnlyTopAppBar
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.User
import com.kelompok1.polnesnews.model.UserRole
import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorSettingsScreen(
    navController: NavHostController,
    currentUser: User?,
    onLogout: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        // ✅ PERBAIKAN: Top Bar dipasang di sini
        topBar = { TitleOnlyTopAppBar(title = "Settings") }
        // 🔴 PERBAIKAN: bottomBar DIHAPUS dari sini
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                text = "Help and Feedback",
                icon = Icons.Outlined.Help,
                onClick = { /* TODO */ }
            )
            SettingsButton(
                text = "Privacy and Policy",
                icon = Icons.Outlined.PrivacyTip,
                onClick = { /* TODO */ }
            )
            SettingsButton(
                text = "About",
                icon = Icons.Outlined.Info,
                onClick = { /* TODO */ }
            )

            SettingsButton(
                text = "Logout",
                icon = Icons.Outlined.Logout,
                onClick = onLogout
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditorSettingsScreenPreview() {
    PolnesNewsTheme {
        val mockUser = DummyData.userList.find { it.role == UserRole.EDITOR }
        EditorSettingsScreen(
            navController = rememberNavController(),
            currentUser = mockUser,
            onLogout = {}
        )
    }
}