package com.example.wishmark.feature_bookmark.domain.repository

import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getAllBookmarks(): Flow<List<Bookmark>>

    suspend fun getBookmark(id: Int): Bookmark?

    fun getBookmarksByCategory(category: Category): Flow<List<Bookmark>>

    suspend fun insertBookmark(bookmark: Bookmark)

    suspend fun deleteBookmark(bookmark: Bookmark)

}