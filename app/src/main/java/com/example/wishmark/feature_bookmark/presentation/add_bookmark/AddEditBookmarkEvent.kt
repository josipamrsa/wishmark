package com.example.wishmark.feature_bookmark.presentation.add_bookmark

import androidx.compose.ui.focus.FocusState
import com.example.wishmark.feature_bookmark.domain.model.Category

sealed class AddEditBookmarkEvent {
    data class TypingTitle(val value: String): AddEditBookmarkEvent()
    data class TypingLink(val value: String): AddEditBookmarkEvent()
    data class SelectedCategory(var category: Category): AddEditBookmarkEvent()
    object SaveBookmark: AddEditBookmarkEvent()
}