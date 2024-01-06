package com.example.wishmark.feature_bookmark.domain.use_case

data class BookmarkUseCases (
    val getBookmark: GetBookmark,
    val deleteBookmark: DeleteBookmark,
    val addBookmark: AddBookmark,
    val getBookmarks: GetBookmarks
)