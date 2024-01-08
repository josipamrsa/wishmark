package com.example.wishmark.feature_bookmark.presentation.add_bookmark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.presentation.add_bookmark.components.CategoryItem
import com.example.wishmark.feature_bookmark.presentation.add_bookmark.components.CategoryMenu
import com.example.wishmark.feature_bookmark.presentation.util.shared.TextInputWithHint
import com.example.wishmark.feature_bookmark.presentation.base.StateScreenWithSecondaryScaffold
import com.example.wishmark.feature_bookmark.presentation.base.UIState
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AddEditBookmarkScreen(
    navController: NavController,
    viewModel: AddEditBookmarkViewModel = hiltViewModel()
) {

    val uiState by viewModel.mutableStateFlow.collectAsStateWithLifecycle()
    val titleState = viewModel.singleBookmarkState.value.title
    val linkState = viewModel.singleBookmarkState.value.link
    val categoriesState = viewModel.singleBookmarkState.value.category


    LaunchedEffect(key1 = true) {
        viewModel.mutableStateFlow.collectLatest {
            if (it == UIState.Success(AddEditBookmarkEvent.SaveBookmark)) {
                navController.navigateUp()
            }
        }
    }

    // TODO goes to vm
    var selectedCategory by remember {
        mutableStateOf(Category.NONE)
    }

    StateScreenWithSecondaryScaffold(
        uiState = UIState.Idle,
        scaffoldTitle = "Add a new wishmark"
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(
                    top = 30.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 30.dp
                )
                .fillMaxSize()

        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CategoryMenu(
                        selectCategory = {
                            viewModel.onEvent(AddEditBookmarkEvent.SelectedCategory(it))
                        }
                    )

                    if (categoriesState != Category.NONE)
                        CategoryItem(categoriesState)
                }


                Spacer(
                    modifier = Modifier
                        .padding(10.dp)
                )

                TextInputWithHint(
                    title = "Title",
                    value = titleState,
                    onValueChange = {
                        viewModel.onEvent(AddEditBookmarkEvent.TypingTitle(it))
                    }
                )

                TextInputWithHint(
                    title = "Link",
                    value = linkState,
                    onValueChange = {
                        viewModel.onEvent(AddEditBookmarkEvent.TypingLink(it))
                    }
                )
            }

            Button(
                onClick = {
                    viewModel.onEvent(AddEditBookmarkEvent.SaveBookmark)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "ADD TO WISHLIST")
            }
        }
    }

}