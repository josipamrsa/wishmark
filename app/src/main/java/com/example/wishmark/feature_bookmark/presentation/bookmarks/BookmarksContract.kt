package com.example.wishmark.feature_bookmark.presentation.bookmarks

import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.presentation.base.UnidirectionalViewModel
import com.fresh.materiallinkpreview.models.OpenGraphMetaData

data class BookmarkItemState(
    val bookmark: Bookmark,
    val linkMetaData: OpenGraphMetaData?
)

interface BookmarksContract :
    UnidirectionalViewModel<BookmarksContract.Event, BookmarksContract.Effect, BookmarksContract.State> {

    data class State(
        val bookmarks: List<BookmarkItemState> = emptyList(),
        val recentlyDeletedBookmark: Bookmark? = null
    )

    sealed class Event {
        data object OnGetAllBookmarks: Event()
        data class OnDeleteBookmark(val bookmark: Bookmark): Event()
        data object OnRestoreBookmark: Event()
        data object OnCreateNewBookmark: Event()
    }

    sealed class Effect {
        data object OnBookmarksFetched: Effect()
        data object OnCreateNewBookmark: Effect()
    }
}