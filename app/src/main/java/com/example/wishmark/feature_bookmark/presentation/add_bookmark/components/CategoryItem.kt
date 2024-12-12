package com.example.wishmark.feature_bookmark.presentation.add_bookmark.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.domain.model.ItemCategory

@Composable
fun CategoryItem(category: ItemCategory) {
    Box(
        modifier = Modifier
            .background(
                color = category.color,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = category.name,
            style = TextStyle(
                fontSize = 12.sp
            )
        )
    }
}