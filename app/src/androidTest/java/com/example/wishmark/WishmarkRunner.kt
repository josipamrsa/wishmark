package com.example.wishmark

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class CustomTestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        // Without HiltTestApplication causes that dreaded it's there but it isn't right error
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}