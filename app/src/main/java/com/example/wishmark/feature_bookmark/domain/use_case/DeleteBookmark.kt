package com.example.wishmark.feature_bookmark.domain.use_case

import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.repository.BookmarkRepository

class DeleteBookmark(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(bookmark: Bookmark) {
        repository.deleteBookmark(bookmark)
    }
}