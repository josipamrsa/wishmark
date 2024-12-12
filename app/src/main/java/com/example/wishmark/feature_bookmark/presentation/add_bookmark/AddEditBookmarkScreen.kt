package com.example.wishmark.feature_bookmark.presentation.add_bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wishmark.R
import com.example.wishmark.feature_bookmark.presentation.add_bookmark.components.CategoryItem
import com.example.wishmark.feature_bookmark.presentation.add_bookmark.components.CategoryMenu
import com.example.wishmark.feature_bookmark.presentation.base.BaseContract
import com.example.wishmark.feature_bookmark.presentation.base.BaseRoute
import com.example.wishmark.feature_bookmark.presentation.util.shared.TextInputWithHint
import com.example.wishmark.feature_bookmark.presentation.base.StateScreenWithSecondaryScaffold
import com.example.wishmark.feature_bookmark.presentation.base.utils.collectInLaunchedEffect
import com.example.wishmark.feature_bookmark.presentation.util.Screen
import com.example.wishmark.feature_bookmark.presentation.util.isNull

fun NavGraphBuilder.addBookmarkScreen(
    navigator: NavHostController,
    onNavigateToBookmarkScreen: () -> Unit
) {
    composable(
        route = "${Screen.AddBookmarkScreen.route}?bookmarkId={bookmarkId}",
        arguments = listOf(navArgument(name = "bookmarkId") {
            type = NavType.IntType
            defaultValue = -1
        })
    ) {
        val viewModel = hiltViewModel<AddEditBookmarkViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        viewModel.effect.collectInLaunchedEffect { effect ->
            when (effect) {
                is AddEditBookmarkContract.Effect.OnNavigateToBookmarkScreen -> {
                    onNavigateToBookmarkScreen()
                }
            }
        }

        BaseRoute(
            baseViewModel = viewModel,
            navigator = navigator
        ) {
            AddEditBookmarkScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}

@Composable
fun AddEditBookmarkScreen(
    state: AddEditBookmarkContract.State,
    onEvent: (AddEditBookmarkContract.Event) -> Unit
) {

    StateScreenWithSecondaryScaffold(
        baseState = BaseContract.BaseState.OnIdle,
        scaffoldTitle = stringResource(id = R.string.wm_screen_add_edit_bookmark_title)
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
                            onEvent(AddEditBookmarkContract.Event.OnCategorySelected(it))
                        }
                    )

                    CategoryItem(state.category)
                }


                Spacer(
                    modifier = Modifier
                        .padding(10.dp)
                )

                TextInputWithHint(
                    title = stringResource(id = R.string.wm_header_title),
                    value = state.title,
                    onValueChange = {
                        onEvent(AddEditBookmarkContract.Event.OnTitleChanged(it))
                    },
                    placeholder = { Text(text = stringResource(id = R.string.wm_enter_title)) },
                    isError = state.title.isNull(),
                    supportingText = {
                        state.titleErrorRes?.let {
                            Text(text = stringResource(id = it))
                        }
                    }
                )

                TextInputWithHint(
                    title = stringResource(id = R.string.wm_header_link),
                    value = state.link,
                    onValueChange = {
                        onEvent(AddEditBookmarkContract.Event.OnLinkChanged(it))
                    },
                    placeholder = { Text(text = stringResource(id = R.string.wm_enter_link)) },
                    isError = state.link.isNull(),
                    supportingText = {
                        state.linkErrorRes?.let {
                            Text(text = stringResource(id = it))
                        }
                    }
                )
            }

            Button(
                onClick = {
                    onEvent(AddEditBookmarkContract.Event.OnSaveBookmark)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.wm_add_to_wishlist).uppercase())
            }
        }
    }

}