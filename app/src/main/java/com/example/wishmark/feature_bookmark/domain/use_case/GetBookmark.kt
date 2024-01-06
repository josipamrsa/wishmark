package com.example.wishmark.feature_bookmark.domain.use_case

import com.example.wishmark.feature_bookmark.domain.repository.BookmarkRepository

class GetBookmark(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.getBookmark(id)
    }
}