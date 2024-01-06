package com.example.wishmark.feature_bookmark.domain.use_case

import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.InvalidBookmarkEntryException
import com.example.wishmark.feature_bookmark.domain.repository.BookmarkRepository
import kotlin.jvm.Throws

class AddBookmark(
    private val repository: BookmarkRepository
) {
    @Throws(InvalidBookmarkEntryException::class)
    suspend operator fun invoke(bookmark: Bookmark) {
        bookmark.apply {
            if (title.isNullOrBlank() || link.isNullOrBlank() || category == null)
                throw InvalidBookmarkEntryException("Neither title, link nor category can be left blank!")
        }
        repository.insertBookmark(bookmark)
    }
}