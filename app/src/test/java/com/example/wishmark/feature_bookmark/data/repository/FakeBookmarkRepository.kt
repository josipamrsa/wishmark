package com.example.wishmark.feature_bookmark.data.repository

import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mongodb.kbson.ObjectId

class FakeBookmarkRepository : BookmarkRepository {
    private val bookmarks = mutableListOf<Bookmark>()
    override fun getAllBookmarks(): Flow<List<Bookmark>> {
        return flow { emit(bookmarks) }
    }

    override suspend fun getBookmark(id: ObjectId): Bookmark? {
        return bookmarks.find { it._id == id }
    }

    override fun getBookmarksByCategory(category: Category): Flow<List<Bookmark>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertBookmark(bookmark: Bookmark) {
        bookmarks.add(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        bookmarks.remove(bookmark)
    }

}