package com.example.wishmark.feature_bookmark.data.repository

import com.example.wishmark.feature_bookmark.data.data_source.BookmarkDao
import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkRepositoryImpl(
    private val dao: BookmarkDao
) : BookmarkRepository{
    override fun getAllBookmarks(): Flow<List<Bookmark>> {
        return dao.getAllBookmarks()
    }

    override suspend fun getBookmark(id: Int): Bookmark? {
        return dao.getBookmark(id)
    }

    override fun getBookmarksByCategory(category: Category): Flow<List<Bookmark>> {
        return getBookmarksByCategory(category)
    }

    override suspend fun insertBookmark(bookmark: Bookmark) {
        dao.insertBookmark(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        dao.deleteBookmark(bookmark)
    }

}