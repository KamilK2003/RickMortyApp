package com.kamilk2003.rickmortyapp.application.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Action> : ViewModel(), DefaultLifecycleObserver {

    private val _state: MutableStateFlow<State> by lazy { MutableStateFlow(initState()) }
    protected val currentState
        get() = _state.value
    val state
        get() = _state as StateFlow<State>

    private val _action = Channel<Action>()
    val action
        get() = _action.receiveAsFlow()

    abstract fun initState(): State

    fun updateState(update: (State) -> State) {
        viewModelScope.launch {
            val updatedState = update.invoke(_state.value)
            _state.emit(updatedState)
        }
    }

    fun sendAction(action: Action) {
        viewModelScope.launch { _action.send(action) }
    }
}