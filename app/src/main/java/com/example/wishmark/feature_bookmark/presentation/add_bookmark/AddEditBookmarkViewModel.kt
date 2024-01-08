package com.example.wishmark.feature_bookmark.presentation.add_bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.domain.use_case.BookmarkUseCases
import com.example.wishmark.feature_bookmark.presentation.base.BaseViewModel
import com.example.wishmark.feature_bookmark.presentation.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddEditBookmarkViewModel @Inject constructor(
    private val bookmarkUseCases: BookmarkUseCases,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(SingleBookmarkState()) {

    private val _singleBookmarkState = mutableStateOf(SingleBookmarkState())
    val singleBookmarkState: State<SingleBookmarkState> = _singleBookmarkState

    private val currentBookmarkId: Int? = null

    fun onEvent(event: AddEditBookmarkEvent) {
        when (event) {
            is AddEditBookmarkEvent.TypingTitle -> {
                _singleBookmarkState.value = singleBookmarkState.value.copy(
                    title = event.value
                )
            }

            is AddEditBookmarkEvent.TypingLink -> {
                _singleBookmarkState.value = singleBookmarkState.value.copy(
                    link = event.value
                )
            }

            is AddEditBookmarkEvent.SelectedCategory -> {
                _singleBookmarkState.value = singleBookmarkState.value.copy(
                    category = event.category
                )
            }

            is AddEditBookmarkEvent.SaveBookmark -> {
                launchWithProgressIn(
                    loadingState = UIState.Loading,
                    successState = UIState.Success(AddEditBookmarkEvent.SaveBookmark)
                ) {
                    addNewBookmark()
                }
            }

            else -> {}
        }
    }

    private suspend fun addNewBookmark() {
        bookmarkUseCases.addBookmark(
            Bookmark(
                title = _singleBookmarkState.value.title,
                link = _singleBookmarkState.value.link,
                category = _singleBookmarkState.value.category,
                id = currentBookmarkId
            )
        )
    }

    private suspend fun addNewBookmarkTest() {
        bookmarkUseCases.addBookmark(
            Bookmark(
                title = "Flipper Zero",
                link = "https://flipperzero.one/",
                category = Category.TECHNOLOGY,
                id = currentBookmarkId
            )
        )
    }
}