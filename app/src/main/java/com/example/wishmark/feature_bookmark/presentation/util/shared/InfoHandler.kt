package com.example.wishmark.feature_bookmark.presentation.util.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.wishmark.feature_bookmark.presentation.base.UIState

@Composable
fun InfoMessageText(
    message: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = message,
        color = color,
        modifier = modifier
    )
}

@Composable
fun ErrorDisplayHandler(error: UIState.Error, color: Color = Color.Red) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        error.throwable.message?.let {
            InfoMessageText(
                message = it,
                color = color
            )
        }
            ?: InfoMessageText(
                message = "Unknown error has occured",
                color = color
            )
    }
}

@Composable
fun InfoDisplayHandler(
    message: String,
    color: Color = Color.DarkGray
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfoMessageText(message = message, color = color)
    }
}
