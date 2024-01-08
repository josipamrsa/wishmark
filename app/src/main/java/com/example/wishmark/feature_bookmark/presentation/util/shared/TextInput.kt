package com.example.wishmark.feature_bookmark.presentation.util.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextInputWithHint(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 11.sp
            ),
            modifier = Modifier
                .padding(5.dp)
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
            ),
            modifier = Modifier.padding(5.dp)
        )

        Divider(
            color = Color.DarkGray,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(
                    start = 5.dp
                )
        )
    }

    Spacer(
        modifier = Modifier
            .padding(10.dp)
    )

}