package com.example.wishmark.feature_bookmark.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishmark.feature_bookmark.presentation.util.trySuspended
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel() :
    ViewModel(), BaseContract {

    protected val mutableBaseState: MutableStateFlow<BaseContract.BaseState> =
        MutableStateFlow(BaseContract.BaseState.OnSuccess)
    override val baseState: StateFlow<BaseContract.BaseState> = mutableBaseState.asStateFlow()

    protected val baseEffectChannel = Channel<BaseContract.BaseEffect>(Channel.UNLIMITED)
    override val baseEffect: Flow<BaseContract.BaseEffect> = baseEffectChannel.receiveAsFlow()

    override fun onBaseEvent(event: BaseContract.BaseEvent) = when (event) {
        BaseContract.BaseEvent.OnBackPressed -> onBackPressed()
    }

    private fun onBackPressed() {
        baseEffectChannel.trySend(BaseContract.BaseEffect.OnBackPressed)
    }

    protected suspend fun <T> trySuspendedErrorHandling(action: suspend () -> T): Result<T> {
        return trySuspended(action).also { result ->
            result.exceptionOrNull()?.let {
                mutableBaseState.emit(BaseContract.BaseState.OnError(it))
            }
        }
    }

    protected fun launchIn(
        coroutineScope: CoroutineScope = viewModelScope,
        action: suspend () -> Unit,
    ) {
        coroutineScope.launch {
            action()
        }
    }

    protected suspend fun launchWithProgress(
        loadingState: BaseContract.BaseState = BaseContract.BaseState.OnLoading,
        successState: BaseContract.BaseState? = BaseContract.BaseState.OnSuccess,
        action: suspend () -> Unit
    ) {
        mutableBaseState.emit(loadingState)
        return trySuspendedErrorHandling(action).let {
            if (successState != null)
                it.getOrNull()?.let { mutableBaseState.emit(successState) }
        }
    }

    protected fun launchWithProgressIn(
        coroutineScope: CoroutineScope = viewModelScope,
        loadingState: BaseContract.BaseState = BaseContract.BaseState.OnLoading,
        successState: BaseContract.BaseState? = BaseContract.BaseState.OnSuccess,
        action: suspend () -> Unit
    ) = coroutineScope.launch {
        launchWithProgress(
            action = action,
            loadingState = loadingState,
            successState = successState
        )
    }.also {
        it.then {
            delay(200)
            if (baseState.value !is BaseContract.BaseState.OnError) {
                mutableBaseState.emit(BaseContract.BaseState.OnSuccess)
            }

        }
    }

    protected fun Job.then(
        coroutineScope: CoroutineScope = viewModelScope,
        action: suspend () -> Unit
    ) = this.invokeOnCompletion {
        coroutineScope.launch { action() }
    }
}

