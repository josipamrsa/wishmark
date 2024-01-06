package com.example.wishmark.feature_bookmark.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wishmark.feature_bookmark.domain.model.Bookmark

@Database(
    entities = [Bookmark::class],
    version = 1
)
abstract class BookmarkDatabase: RoomDatabase() {
    abstract val bookmarkDao: BookmarkDao

    companion object {
        const val DATABASE_NAME = "wishmark_db"
    }
}