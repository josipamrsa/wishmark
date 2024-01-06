package com.example.wishmark.feature_bookmark.presentation.util.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WishmarkHeader() {
    Text(
        text = "Wishmark",
        style = TextStyle(
            fontSize = 22.sp
        ),
        modifier = Modifier.padding(20.dp)
    )
}