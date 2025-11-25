package com.kelompok1.polnesnews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok1.polnesnews.model.UserRole

/**
 * Composable reusable untuk menampilkan kartu info akun.
 * Menggunakan ikon lokal dengan warna dinamis berdasarkan Role.
 */
@Composable
fun AccountInfoCard(
    modifier: Modifier = Modifier,
    fullName: String,
    role: String
) {
    // Tentukan warna background dan warna ikon/teks berdasarkan Role
    val (bgColor, contentColor) = when (role) {
        UserRole.EDITOR -> Pair(Color(0xFFE3F2FD), Color(0xFF2196F3)) // Biru (Pending/Editor style)
        UserRole.USER -> Pair(Color(0xFFE8F5E9), Color(0xFF4CAF50))   // Hijau (Published/User style)
        UserRole.ADMIN -> Pair(Color(0xFFFFF3E0), Color(0xFFFF9800))  // Orange (Draft/Admin style)
        else -> Pair(Color(0xFFF5F5F5), Color(0xFF9E9E9E))            // Default Abu-abu
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Bagian Ikon Profil
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(bgColor), // Warna background dinamis
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Icon",
                    tint = contentColor, // Warna ikon dinamis
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Bagian Teks
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = fullName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                // Menampilkan Role (bisa juga diganti email jika data tersedia di parameter)
                Text(
                    text = role,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor, // Teks role warnanya disamakan dengan ikon agar serasi
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountInfoCardPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        AccountInfoCard(fullName = "Ade Darmawan", role = UserRole.EDITOR)
        AccountInfoCard(fullName = "John Doe", role = UserRole.USER)
        AccountInfoCard(fullName = "Admin Polnes", role = UserRole.ADMIN)
    }
}