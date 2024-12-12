package com.example.wishmark

import android.app.Application
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.internal.managers.ApplicationComponentManager

open class BaseApp : Application() { }

@HiltAndroidApp
class WishmarkApp : BaseApp()