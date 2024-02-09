package com.example.wishmark.feature_bookmark.presentation.util.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
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
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
    placeholder:  @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = TextStyle.Default.copy(
        fontSize = 18.sp,
        color = Color.White,
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
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

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            enabled = enabled,
            isError = isError,
            supportingText = supportingText,
            placeholder = placeholder,
            label = label,
            textStyle = textStyle,
            modifier = modifier.padding(5.dp),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
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