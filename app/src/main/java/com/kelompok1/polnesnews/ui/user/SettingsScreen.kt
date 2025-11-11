package com.kelompok1.polnesnews.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Help
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
import com.kelompok1.polnesnews.model.DummyData
import com.kelompok1.polnesnews.model.UserRole
// import com.kelompok1.polnesnews.ui.theme.PolnesNewsTheme

/**
 * Layar Pengaturan (Settings).
 * Layar ini 'stateless' untuk aksi logout, ia menerima fungsi [onLogout]
 * dari pemanggil (NavGraph) untuk dieksekusi saat tombol Logout diklik.
 */
@Composable
fun SettingsScreen(
    onLogout: () -> Unit // Lambda yang akan dipanggil untuk proses logout
) {
    // Ambil data user (dummy) untuk ditampilkan di card.
    // TODO: Ganti ini dengan data user yang login sesungguhnya.
    val currentUser = DummyData.userList.find { it.role == UserRole.USER }

    // Pakai Column + verticalScroll agar layar bisa di-scroll
    // jika item pengaturannya nanti jadi banyak.
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
            fullName = currentUser?.name ?: "User Not Found",
            // Logika kecil untuk format nama Role (misal: "USER" -> "User")
            role = currentUser?.role?.name?.let {
                it.lowercase().replaceFirstChar { char -> char.uppercase() }
            } ?: "Guest"
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
            onClick = { /* TODO: Handle Klik, misal buka link */ }
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
            onClick = onLogout // Panggil lambda 'onLogout' dari NavGraph saat diklik
        )
    }
}

// --- PREVIEW 1: HANYA KONTEN ---
@Preview(showBackground = true, name = "Hanya Konten (Default)")
@Composable
private fun SettingsScreenPreview() {
    // PolnesNewsTheme {
    // Sediakan lambda kosong ({}) agar preview bisa dirender
    SettingsScreen(
        onLogout = {}
    )
    // }
}

// --- PREVIEW 2: TAMPILAN PENUH ---
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Tampilan Penuh (Full App)")
@Composable
private fun FullSettingsScreenPreview() {
    // PolnesNewsTheme {
    // Simulasi layar penuh lengkap dengan TopBar dan BottomNav
    Scaffold(
        topBar = {
            TitleOnlyTopAppBar(title = "Settings")
        },
        bottomBar = {
            UserBottomNav(
                currentRoute = "Settings", // Tandai 'Settings' sebagai aktif
                onItemClick = {}
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // Sediakan lambda kosong ({}) untuk kebutuhan preview
            SettingsScreen(
                onLogout = {}
            )
        }
    }
    // }
}