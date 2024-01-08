package com.example.wishmark.feature_bookmark.presentation.bookmarks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.domain.use_case.BookmarkUseCases
import com.example.wishmark.feature_bookmark.presentation.base.BaseViewModel
import com.example.wishmark.feature_bookmark.presentation.base.UIState
import com.fresh.materiallinkpreview.models.OpenGraphMetaData
import com.fresh.materiallinkpreview.parsing.OpenGraphMetaDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val bookmarkUseCases: BookmarkUseCases
) : BaseViewModel(BookmarksState()) {

    // TODO maybe provide as DI
    private val openGraphMetaDataProvider = OpenGraphMetaDataProvider()

    private val _bookmarksState = mutableStateOf<BookmarksState>(BookmarksState())
    val bookmarksState: State<BookmarksState> = _bookmarksState

    private var recentlyDeletedBookmark: Bookmark? = null
    private var getBookmarksJob: Job? = null

    private suspend fun getBookmarks() {
        bookmarkUseCases.getBookmarks().onEach { bookmarks ->
            _bookmarksState.value = bookmarksState.value.copy(
                bookmarks.map {
                    BookmarkItemState(
                        bookmark = it,
                        linkMetaData = downloadLinkMetaData(it.link)
                    )
                }
            )
        }.stateIn(viewModelScope)
    }


    init {
        launchWithProgressIn(
            loadingState = UIState.Loading,
            successState = UIState.Success(Unit)
        ) {
            getBookmarks()
        }

    }

    fun onEvent(event: BookmarksEvent) {
        when (event) {
            is BookmarksEvent.DeleteBookmark -> {
                launchIn {
                    bookmarkUseCases.deleteBookmark(event.bookmark)
                    recentlyDeletedBookmark = event.bookmark
                }

            }

            is BookmarksEvent.RestoreBookmark -> {
                launchIn {
                    bookmarkUseCases.addBookmark(recentlyDeletedBookmark ?: return@launchIn)
                    recentlyDeletedBookmark = null
                }
            }
        }
    }

    suspend fun downloadLinkMetaData(link: String): OpenGraphMetaData? {
        return openGraphMetaDataProvider.startFetchingMetadataAsync(URL(link)).getOrNull()
    }


    // TODO proper testing lol

    /* TESTS */
    private fun fillWithBookmarksTest() {

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