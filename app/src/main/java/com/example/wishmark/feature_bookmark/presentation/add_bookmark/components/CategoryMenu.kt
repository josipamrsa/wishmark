package com.example.wishmark.feature_bookmark.presentation.add_bookmark.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wishmark.feature_bookmark.domain.model.Category

@Composable
fun CategoryMenu(selectCategory: (Category) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .clickable { expanded = !expanded }
    ) {
        Row {
            Text(text = "Category")
            Icon(
                imageVector = if (!expanded)
                    Icons.Filled.KeyboardArrowDown
                else Icons.Filled.KeyboardArrowUp,
                contentDescription = "Open DropDown menu"
            )
        }

        Spacer(
            modifier = Modifier
                .padding(10.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },

            ) {
            Category.entries
                .filter { it != Category.NONE }
                .forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option.name,
                                modifier = Modifier
                                    .padding(7.dp)
                            )
                        },
                        onClick = {
                            selectCategory(option)
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = Color.White
                        )
                    )

                    Divider(
                        thickness = 0.5.dp,
                        color = Color.DarkGray
                    )
                }

        }
    }
}