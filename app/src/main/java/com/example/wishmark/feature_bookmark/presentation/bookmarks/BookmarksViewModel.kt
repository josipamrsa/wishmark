package com.example.wishmark.feature_bookmark.presentation.bookmarks

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.ItemCategory
import com.example.wishmark.feature_bookmark.domain.use_case.BookmarkUseCases
import com.example.wishmark.feature_bookmark.presentation.base.BaseViewModel
import com.example.wishmark.feature_bookmark.presentation.util.toCategory
import com.fresh.materiallinkpreview.parsing.OpenGraphMetaDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
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
            is BookmarksContract.Event.OnGetAllBookmarks -> launchIn {
                getBookmarks()
                //fillWithBookmarksTest()
            }

            is BookmarksContract.Event.OnDeleteBookmark -> {}
            is BookmarksContract.Event.OnRestoreBookmark -> {}
            is BookmarksContract.Event.OnCreateNewBookmark -> launchIn {
                effectFlow.emit(
                    BookmarksContract.Effect.OnCreateNewBookmark
                )
            }
        }
    }

    private suspend fun getBookmarks() {
        launchWithProgressIn {
            bookmarkUseCases.getBookmarks().onEach { data ->
                Log.d("WISHMARK", data.map { it._id }.toString())
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
            }.stateIn(viewModelScope)

            effectFlow.emit(BookmarksContract.Effect.OnBookmarksFetched)
        }
    }


    // TODO proper testing lol

    /* TESTS */
    fun fillWithBookmarksTest() {
        val b1 = Bookmark().apply {
            this.title = "Flipper Zero"
            this.link = "https://flipperzero.one/"
            this.category = toCategory(ItemCategory.TECHNOLOGY)
        }

        val b2 = Bookmark().apply {
            this.title = "Apple iPad 9 64GB"
            this.link =
                "https://www.sancta-domenica.hr/komunikacije/tableti/tablet-apple-10-2-inch-ipad-9-wi-fi-64gb-space-grey.html"
            this.category = toCategory(ItemCategory.TECHNOLOGY)
        }

        val testBookmarks = listOf<Bookmark>(
            b1, b2
        )

        launchIn {
            testBookmarks.forEach { bookmark ->
                bookmarkUseCases.addBookmark(bookmark)
            }
        }
    }
}