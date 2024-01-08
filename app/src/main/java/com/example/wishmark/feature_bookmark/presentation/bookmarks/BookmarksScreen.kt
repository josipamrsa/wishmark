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
import androidx.navigation.NavController
import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.presentation.base.StateScreenWithMainScaffold
import com.example.wishmark.feature_bookmark.presentation.bookmarks.components.BookmarkItem
import com.example.wishmark.feature_bookmark.presentation.util.Screen
import com.example.wishmark.feature_bookmark.presentation.util.shared.InfoDisplayHandler

@Composable
fun BookmarksScreenBody(
    bookmarks: List<BookmarkItemState>,
    onDeleteBookmark: (Bookmark) -> Unit
) {
    if (bookmarks.isNullOrEmpty()) {
        InfoDisplayHandler(message = "No wishmarks to display")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(bookmarks) { bookmark ->
            BookmarkItem(
                bookmarkItem = bookmark,
                onDeleteBookmark = onDeleteBookmark
            )
        }
    }
}

@Composable
fun BookmarksScreen(
    navController: NavController,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val uiState by viewModel.mutableStateFlow.collectAsStateWithLifecycle()
    val bookmarksState = viewModel.bookmarksState.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    StateScreenWithMainScaffold(
        uiState = uiState,
        isFabVisible = true,
        fabAction = { navController.navigate(Screen.AddBookmarkScreen.route) },
    ) {
        BookmarksScreenBody(
            bookmarks = bookmarksState.bookmarks,
            onDeleteBookmark = {
                viewModel.onEvent(BookmarksEvent.DeleteBookmark(it))
            }
        )
    }
}