package com.example.wishmark.feature_bookmark.presentation.bookmarks

import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.fresh.materiallinkpreview.models.OpenGraphMetaData

/*data class BookmarkItemState (
    val bookmark: Bookmark,
    val linkMetaData: OpenGraphMetaData?
)*/

data class BookmarksState (
    val bookmarks: List<BookmarkItemState> = emptyList(),
)