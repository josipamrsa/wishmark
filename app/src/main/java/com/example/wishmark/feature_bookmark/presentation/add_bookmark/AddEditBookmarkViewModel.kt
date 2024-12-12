package com.example.wishmark.feature_bookmark.presentation.add_bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.wishmark.R
import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.domain.model.ItemCategory
import com.example.wishmark.feature_bookmark.domain.use_case.BookmarkUseCases
import com.example.wishmark.feature_bookmark.presentation.base.BaseContract
import com.example.wishmark.feature_bookmark.presentation.base.BaseViewModel
import com.example.wishmark.feature_bookmark.presentation.util.toCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddEditBookmarkViewModel @Inject constructor(
    private val bookmarkUseCases: BookmarkUseCases,
) : BaseViewModel(), AddEditBookmarkContract {

    protected val mutableState = MutableStateFlow(AddEditBookmarkContract.State())
    override val state: StateFlow<AddEditBookmarkContract.State> = mutableState.asStateFlow()

    protected val effectFlow = MutableSharedFlow<AddEditBookmarkContract.Effect>()
    override val effect: SharedFlow<AddEditBookmarkContract.Effect> = effectFlow.asSharedFlow()

    override fun onEvent(event: AddEditBookmarkContract.Event) {
        when (event) {
            is AddEditBookmarkContract.Event.OnTitleChanged -> handleTitleChanged(event.title)
            is AddEditBookmarkContract.Event.OnLinkChanged -> handleLinkChanged(event.link)
            is AddEditBookmarkContract.Event.OnCategorySelected -> handleCategoryChanged(event.category)
            is AddEditBookmarkContract.Event.OnSaveBookmark -> launchIn { addNewBookmark() }
            else -> {}
        }
    }

    private fun handleTitleChanged(title: String) {
        mutableState.update {
            it.copy(title = title)
        }

        mutableState.update {
            it.copy(
                titleErrorRes = when {
                    title.isEmpty() -> R.string.wm_title_empty_error
                    else -> null
                }
            )
        }
    }

    private fun handleLinkChanged(link: String) {
        mutableState.update {
            it.copy(link = link)
        }

        mutableState.update {
            it.copy(
                linkErrorRes = when {
                    link.isEmpty() -> R.string.wm_link_empty_error
                    else -> null
                }
            )
        }
    }

    private fun handleCategoryChanged(category: ItemCategory) {
        mutableState.update {
            it.copy(category = category)
        }
    }

    private suspend fun addNewBookmark() {
        launchWithProgressIn {
            bookmarkUseCases.addBookmark(
                Bookmark().apply {
                    this.title = mutableState.value.title
                    this.link = mutableState.value.link
                    this.category = toCategory(mutableState.value.category)
                }
            )

            effectFlow.emit(AddEditBookmarkContract.Effect.OnNavigateToBookmarkScreen)
        }
    }

    private suspend fun addNewBookmarkTest() {
        /*bookmarkUseCases.addBookmark(
            Bookmark(
                title = "Flipper Zero",
                link = "https://flipperzero.one/",
                category = Category.TECHNOLOGY,
                id = currentBookmarkId
            )
        )*/
    }

}