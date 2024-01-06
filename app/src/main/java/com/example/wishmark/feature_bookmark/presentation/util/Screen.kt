package com.example.wishmark.feature_bookmark.presentation.util

sealed class Screen(val route: String) {
    object BookmarksScreen: Screen("bookmarks_screen")
}