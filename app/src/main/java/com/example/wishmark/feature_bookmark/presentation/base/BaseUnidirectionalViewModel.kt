package com.example.wishmark.feature_bookmark.presentation.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface UnidirectionalViewModel<EVENT, EFFECT, STATE> {
    val state: StateFlow<STATE>
    val effect: SharedFlow<EFFECT>
    fun onEvent(event: EVENT)
}

interface BaseUnidirectionalViewModel<BASE_EVENT, BASE_EFFECT, BASE_STATE> {
    val baseState: StateFlow<BASE_STATE>
    val baseEffect: Flow<BASE_EFFECT>
    fun onBaseEvent(event: BASE_EVENT)
}

data class StateEffectDispatch<EVENT, EFFECT, STATE>(
    val state: STATE,
    val effectFlow: Flow<EFFECT>,
    val dispatch: (EVENT) -> Unit
)

data class StateDispatch<EVENT, STATE>(
    val state: STATE,
    val dispatch: (EVENT) -> Unit
)







