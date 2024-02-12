package com.example.wishmark.feature_bookmark.presentation.util

import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.domain.model.ItemCategory

fun toCategory(category: ItemCategory) = Category().apply { this.name = category.name }

fun toItemCategory(category: Category?) = ItemCategory
    .entries
    .filter { it.name == category?.name }.first()