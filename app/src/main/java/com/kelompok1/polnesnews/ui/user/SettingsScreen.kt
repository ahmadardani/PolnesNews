package com.kelompok1.polnesnews.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok1.polnesnews.components.AccountInfoCard
import com.kelompok1.polnesnews.components.SettingsButton
import com.kelompok1.polnesnews.utils.SessionManager

@Composable
fun SettingsScreen(
    onLogout: () -> Unit,
    onPrivacyClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    val currentUser = SessionManager.currentUser

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
            fullName = currentUser?.name ?: "Guest User",
            // 🟢 REVISI PENTING DI SINI:
            // Hapus '.name' karena role sekarang adalah String.
            role = currentUser?.role?.let {
                // Format String: "EDITOR" -> "Editor"
                it.lowercase().replaceFirstChar { char -> char.uppercase() }
            } ?: "Guest"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Bagian Tombol ---
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

        SettingsButton(
            text = "Logout",
            icon = Icons.Outlined.Logout,
            onClick = onLogout
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(
        onLogout = {},
        onPrivacyClick = {},
        onAboutClick = {}
    )
}