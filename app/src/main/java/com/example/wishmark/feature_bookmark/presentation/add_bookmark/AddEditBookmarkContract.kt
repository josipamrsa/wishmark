package com.example.wishmark.feature_bookmark.presentation.add_bookmark

import androidx.annotation.StringRes
//import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.domain.model.ItemCategory
import com.example.wishmark.feature_bookmark.presentation.base.BaseUnidirectionalViewModel
import com.example.wishmark.feature_bookmark.presentation.base.UnidirectionalViewModel

interface AddEditBookmarkContract :
    UnidirectionalViewModel<AddEditBookmarkContract.Event, AddEditBookmarkContract.Effect, AddEditBookmarkContract.State> {
    data class State(
        val title: String = "",
        val link: String = "",
        val category: ItemCategory = ItemCategory.NONE,
        val id: Int? = null,
        @StringRes val titleErrorRes: Int? = null,
        @StringRes val linkErrorRes: Int? = null,
        @StringRes val categoryErrorRes: Int? = null
    )

    sealed class Event {
        data class OnTitleChanged(val title: String) : Event()
        data class OnLinkChanged(val link: String) : Event()
        data class OnCategorySelected(val category: ItemCategory) : Event()
        data object OnSaveBookmark : Event()
    }

    sealed class Effect {
        data object OnNavigateToBookmarkScreen : Effect()
    }
}