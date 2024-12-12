package com.example.wishmark.feature_bookmark.presentation.base

interface BaseContract : BaseUnidirectionalViewModel<BaseContract.BaseEvent, BaseContract.BaseEffect, BaseContract.BaseState> {
    sealed class BaseState {
        data object OnIdle: BaseState()
        data object OnLoading : BaseState()
        data object OnLoadingDialog: BaseState()
        data class OnError(val error: Throwable) : BaseState()
        data object OnSuccess : BaseState()
    }

    sealed class BaseEffect {
        data object OnBackPressed : BaseEffect()
    }

    sealed class BaseEvent {
        data object OnBackPressed : BaseEvent()
    }
}