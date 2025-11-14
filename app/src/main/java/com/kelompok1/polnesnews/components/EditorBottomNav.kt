package com.kelompok1.polnesnews.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier // 🔹 Import ditambahkan
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp // 🔹 Import ditambahkan

data class EditorNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun EditorBottomNav(
    modifier: Modifier = Modifier, // 🔹 Modifier ditambahkan
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        EditorNavItem("Your Articles", Icons.Default.Article, "editor_articles"),
        EditorNavItem("Dashboard", Icons.Default.BarChart, "editor_dashboard"),
        EditorNavItem("Settings", Icons.Default.Settings, "editor_settings")
    )

    NavigationBar(
        modifier = modifier, // 🔹 Modifier diterapkan
        containerColor = MaterialTheme.colorScheme.primary, // 🔹 Warna container
        tonalElevation = 3.dp // 🔹 Elevasi
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route // 🔹 Variabel untuk keterpilihan

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = {
                    // 🔹 Text label dengan pewarnaan kustom
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isSelected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                    )
                },
                // 🔹 Pewarnaan item dari NavigationBarItemDefaults
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )
            )
        }
    }
}