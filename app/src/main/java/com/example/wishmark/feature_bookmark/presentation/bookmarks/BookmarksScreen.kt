package com.example.wishmark.feature_bookmark.presentation.bookmarks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.presentation.base.BaseContract
import com.example.wishmark.feature_bookmark.presentation.base.BaseRoute
import com.example.wishmark.feature_bookmark.presentation.base.StateScreenWithMainScaffold
import com.example.wishmark.feature_bookmark.presentation.base.utils.collectInLaunchedEffect
import com.example.wishmark.feature_bookmark.presentation.bookmarks.components.BookmarkItem
import com.example.wishmark.feature_bookmark.presentation.util.Screen
import com.example.wishmark.feature_bookmark.presentation.util.shared.InfoDisplayHandler

fun NavGraphBuilder.bookmarksScreen(
    navigator: NavHostController,
    onCreateNewBookmark: () -> Unit
) {
    composable(
        route = Screen.BookmarksScreen.route
    ) {
        val viewModel = hiltViewModel<BookmarksViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        viewModel.effect.collectInLaunchedEffect { effect ->
            when (effect) {
                is BookmarksContract.Effect.OnBookmarksFetched -> {  }
                is BookmarksContract.Effect.OnCreateNewBookmark -> { onCreateNewBookmark() }
            }
        }

        BaseRoute(
            baseViewModel = viewModel,
            navigator = navigator
        ) {
            BookmarksScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}

@Composable
fun BookmarksScreenBody(
    bookmarks: List<BookmarkItemState>,
    onDeleteBookmark: (Bookmark) -> Unit
) {
    if (bookmarks.isEmpty()) {
        InfoDisplayHandler(message = "No wishmarks to display")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(bookmarks) { bookmarkItem ->
            BookmarkItem(
                bookmarkItem = bookmarkItem,
                onDeleteBookmark = { onDeleteBookmark(bookmarkItem.bookmark) }
            )
        }
    }
}

@Composable
fun BookmarksScreen(
    state: BookmarksContract.State,
    onEvent: (BookmarksContract.Event) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    onEvent(BookmarksContract.Event.OnGetAllBookmarks)

    StateScreenWithMainScaffold(
        baseState = BaseContract.BaseState.OnIdle,
        isFabVisible = true,
        fabAction = { onEvent(BookmarksContract.Event.OnCreateNewBookmark) },
    ) {
        BookmarksScreenBody(
            bookmarks = state.bookmarks,
            onDeleteBookmark = {
                onEvent(BookmarksContract.Event.OnDeleteBookmark(it))
            }
        )
    }
}