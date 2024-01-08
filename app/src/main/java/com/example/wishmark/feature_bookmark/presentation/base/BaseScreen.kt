package com.example.wishmark.feature_bookmark.presentation.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wishmark.feature_bookmark.presentation.util.shared.CircularLoaderIndicator
import com.example.wishmark.feature_bookmark.presentation.util.shared.ErrorDisplayHandler
import com.example.wishmark.feature_bookmark.presentation.util.shared.WishmarkHeader

@Composable
fun StateScreenWithMainScaffold(
    uiState: UIState,
    modifier: Modifier = Modifier,
    isFabVisible: Boolean,
    fabAction: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            )
            {
                WishmarkHeader()
            }
        },
        floatingActionButton = {
            AnimatedVisibility(visible = isFabVisible) {
                FloatingActionButton(
                    onClick = fabAction
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add a wishmark")
                }
            }
        },

        ) { paddingValues ->

        Box(
            modifier
                .padding(paddingValues)
        ) {
            when (uiState) {
                is UIState.Loading -> CircularLoaderIndicator()
                is UIState.Error -> {
                    ErrorDisplayHandler(uiState)
                }

                else -> Column { content() }
            }
        }

    }
}

@Composable
fun StateScreenWithSecondaryScaffold(
    uiState: UIState,
    scaffoldTitle: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {

    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
            ) {
                Text(
                    text = scaffoldTitle,
                    style = TextStyle(
                        fontSize = 20.sp
                    )
                )
            }
        },
    ) { paddingValues ->
        Box(
            modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is UIState.Loading -> CircularLoaderIndicator()
                is UIState.Error -> {
                    ErrorDisplayHandler(uiState)
                }

                else -> Column { content() }
            }
        }
    }

}