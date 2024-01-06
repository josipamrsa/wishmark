package com.example.wishmark.feature_bookmark.domain.use_case

import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class GetBookmarks(
    private val repository: BookmarkRepository
) {
    operator fun invoke(category: Category? = null): Flow<List<Bookmark>> {
        return if (category == null)
            repository.getAllBookmarks()
        else repository.getBookmarksByCategory(category)
    }
}