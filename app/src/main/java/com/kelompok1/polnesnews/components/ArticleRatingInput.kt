package com.kelompok1.polnesnews.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * A 5-star rating input component.
 * Used in article detail pages for user feedback.
 */
@Composable
fun ArticleRatingInput(
    modifier: Modifier = Modifier,
    onRatingSelected: (Int) -> Unit = {}
) {
    var userRating by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rate this article",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Row(modifier = Modifier.padding(top = 8.dp)) {
            (1..5).forEach { index ->
                IconButton(onClick = {
                    userRating = index
                    onRatingSelected(index)
                }) {
                    Icon(
                        imageVector = if (index <= userRating)
                            Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Rate $index",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}
