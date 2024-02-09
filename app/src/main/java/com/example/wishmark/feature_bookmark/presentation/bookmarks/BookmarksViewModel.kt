package com.example.wishmark.feature_bookmark.presentation.bookmarks

import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.domain.use_case.BookmarkUseCases
import com.example.wishmark.feature_bookmark.presentation.base.BaseViewModel
import com.fresh.materiallinkpreview.models.OpenGraphMetaData
import com.fresh.materiallinkpreview.parsing.OpenGraphMetaDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val bookmarkUseCases: BookmarkUseCases
) : BaseViewModel(), BookmarksContract {

    // TODO maybe provide as DI
    private val openGraphMetaDataProvider = OpenGraphMetaDataProvider()

    protected val mutableState = MutableStateFlow(BookmarksContract.State())
    override val state: StateFlow<BookmarksContract.State> = mutableState.asStateFlow()

    protected val effectFlow = MutableSharedFlow<BookmarksContract.Effect>()
    override val effect: SharedFlow<BookmarksContract.Effect> = effectFlow.asSharedFlow()

    override fun onEvent(event: BookmarksContract.Event) {
        when (event) {
            is BookmarksContract.Event.OnGetAllBookmarks -> launchIn { getBookmarks() }
            is BookmarksContract.Event.OnDeleteBookmark -> {}
            is BookmarksContract.Event.OnRestoreBookmark -> {}
            is BookmarksContract.Event.OnCreateNewBookmark -> launchIn { effectFlow.emit(BookmarksContract.Effect.OnCreateNewBookmark) }
        }
    }

    private suspend fun getBookmarks() {
        launchWithProgressIn {
            bookmarkUseCases.getBookmarks().onEach { data ->
                mutableState.update {
                    it.copy(
                        bookmarks = data.map {
                            BookmarkItemState(
                                bookmark = it,
                                linkMetaData = openGraphMetaDataProvider.startFetchingMetadataAsync(
                                    URL(it.link)
                                ).getOrNull()
                            )
                        }
                    )
                }

            }

            effectFlow.emit(BookmarksContract.Effect.OnBookmarksFetched)
        }


        // TODO proper testing lol

        /* TESTS */
        fun fillWithBookmarksTest() {

            val testBookmarks = listOf<Bookmark>(
                Bookmark("Flipper Zero", "https://flipperzero.one/", Category.TECHNOLOGY),
                Bookmark(
                    "Apple iPad 9 64GB",
                    "https://www.sancta-domenica.hr/komunikacije/tableti/tablet-apple-10-2-inch-ipad-9-wi-fi-64gb-space-grey.html",
                    Category.TECHNOLOGY
                )
            )

            launchIn {
                testBookmarks.forEach { bookmark ->
                    bookmarkUseCases.addBookmark(bookmark)
                }
            }

        }

    }
}