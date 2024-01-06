package com.example.wishmark.feature_bookmark.presentation.util

import timber.log.Timber

suspend fun <T> trySuspended(action: suspend () -> T): Result<T> {
    return try {
        Result.success(action.invoke())
    } catch (e: Exception) {
        Timber.e(e.message)
        Result.failure(e)
    }
}