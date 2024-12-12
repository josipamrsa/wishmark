package com.example.wishmark.feature_bookmark.presentation.base.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.wishmark.feature_bookmark.presentation.base.BaseUnidirectionalViewModel
import com.example.wishmark.feature_bookmark.presentation.base.StateDispatch
import com.example.wishmark.feature_bookmark.presentation.base.StateEffectDispatch
import com.example.wishmark.feature_bookmark.presentation.base.UnidirectionalViewModel

@Composable
inline fun <reified EVENT, EFFECT, STATE> use(
    viewModel: UnidirectionalViewModel<EVENT, EFFECT, STATE>,
) : StateDispatch<EVENT, STATE> {
    val state by viewModel.state.collectAsState()

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.onEvent(event)
    }

    return StateDispatch(
        state = state,
        dispatch = dispatch
    )
}

@Composable
inline fun <reified BASE_EVENT, BASE_EFFECT, BASE_STATE> useBase(
    viewModel: BaseUnidirectionalViewModel<BASE_EVENT, BASE_EFFECT, BASE_STATE>,
) : StateEffectDispatch<BASE_EVENT, BASE_EFFECT, BASE_STATE> {
    val state by viewModel.baseState.collectAsState()

    val dispatch: (BASE_EVENT) -> Unit = { event ->
        viewModel.onBaseEvent(event)
    }

    return StateEffectDispatch(
        state = state,
        effectFlow = viewModel.baseEffect,
        dispatch = dispatch
    )
}