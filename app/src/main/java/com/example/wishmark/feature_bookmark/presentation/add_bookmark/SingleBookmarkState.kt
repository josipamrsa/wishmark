package com.example.wishmark.feature_bookmark.presentation.add_bookmark

import com.example.wishmark.feature_bookmark.domain.model.Category

data class SingleBookmarkState (
    val title: String = "",
    val link: String = "",
    val category: Category = Category.NONE
)

