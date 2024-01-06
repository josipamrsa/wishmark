package com.example.wishmark.feature_bookmark.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishmark.feature_bookmark.presentation.util.trySuspended
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(state: Any) : ViewModel() {
    protected val _mutableState = MutableStateFlow<UIState>(UIState.Idle)
    val mutableStateFlow: StateFlow<UIState>
        get() = _mutableState


    protected suspend fun <T> trySuspendedErrorHandling(action: suspend () -> T): Result<T> {
        return trySuspended(action).also { result ->
            result.exceptionOrNull()?.let { _mutableState.emit(UIState.Error(it)) }
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
        loadingState: UIState = UIState.Loading,
        successState: UIState? = UIState.Idle,
        action: suspend () -> Unit
    ) {
        _mutableState.emit(loadingState)
        return trySuspendedErrorHandling(action).let {
            if (successState != null)
                it.getOrNull()?.let { _mutableState.emit(successState) }
        }
    }

    protected fun launchWithProgressIn(
        coroutineScope: CoroutineScope = viewModelScope,
        loadingState: UIState = UIState.Loading,
        successState: UIState? = UIState.Idle,
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
            if (mutableStateFlow.value !is UIState.Error)
                _mutableState.emit(UIState.Idle)
        }
    }

    protected fun Job.then(
        coroutineScope: CoroutineScope = viewModelScope,
        action: suspend () -> Unit
    ) = this.invokeOnCompletion {
        coroutineScope.launch { action() }
    }


    // TODO proper testing lol

    /* TESTS */

    protected suspend fun launchWithProgressErrorTest(
        loadingState: UIState = UIState.Loading,
        successState: UIState? = UIState.Idle,
        action: suspend () -> Unit
    ) {
        _mutableState.emit(loadingState)
        return trySuspendedErrorHandling(action).let {
            if (successState != null)
                it.getOrNull()?.let { _mutableState.emit(UIState.Error(Exception("KONJSKI"))) }
        }
    }

}


interface UIState {
    object Idle : UIState
    object Loading : UIState
    data class Error(val throwable: Throwable) : UIState
    data class Success<out R>(val resource: R) : UIState
}