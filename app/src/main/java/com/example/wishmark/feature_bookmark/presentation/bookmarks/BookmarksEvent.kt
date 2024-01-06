package com.example.wishmark.feature_bookmark.presentation.bookmarks

import com.example.wishmark.feature_bookmark.domain.model.Bookmark

sealed class BookmarksEvent {
    data class DeleteBookmark(val bookmark: Bookmark) : BookmarksEvent()
    object RestoreBookmark: BookmarksEvent()
}