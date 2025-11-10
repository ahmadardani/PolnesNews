package com.kelompok1.polnesnews.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok1.polnesnews.model.Notification
import androidx.compose.ui.tooling.preview.Preview
import com.kelompok1.polnesnews.model.DummyData

@Composable
fun NotificationCard(notification: Notification) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        // 🔹 Tanggal di atas card
        Text(
            text = notification.date,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .padding(start = 8.dp, bottom = 4.dp)
        )

        // 🔹 Isi card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(3.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ) {
                // Icon di kiri
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFA3E5A6)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = notification.iconRes),
                        contentDescription = "Notification Icon",
                        colorFilter = ColorFilter.tint(Color(0xFF50AE5E)),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Teks di kanan
                Column {
                    Text(
                        text = "New article from ${notification.category}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp
                        ),
                        color = Color(0xFF375E2F) // <-- DIUBAH DI SINI
                    )
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// Preview akan otomatis diperbarui
@Preview(showBackground = true)
@Composable
fun NotificationCardPreview() {
    val sampleNotification = DummyData.notifications[0]
    NotificationCard(notification = sampleNotification)
}