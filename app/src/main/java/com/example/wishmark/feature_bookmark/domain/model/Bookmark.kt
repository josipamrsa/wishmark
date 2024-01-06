package com.example.wishmark.feature_bookmark.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bookmark(
    val title: String,
    val link: String,
    val category: Category,
    @PrimaryKey val id: Int? = null
)

class InvalidBookmarkEntryException(message: String): Exception(message)