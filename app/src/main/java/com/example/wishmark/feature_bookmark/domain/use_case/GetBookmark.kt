package com.example.wishmark.feature_bookmark.domain.use_case

import com.example.wishmark.feature_bookmark.domain.repository.BookmarkRepository
import org.mongodb.kbson.ObjectId

class GetBookmark(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(id: ObjectId) {
        repository.getBookmark(id)
    }
}